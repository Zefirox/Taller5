package co.edu.unbosque.taller5.services;

import co.edu.unbosque.taller5.dtos.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/taller5";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "Zeref29714526?";

    public Optional<List<User>> getUsers(Connection connection) throws IOException{

        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<User> users = new ArrayList<User>();

        try {
            // Executing a SQL query
            stmt = connection.createStatement();
            String sql = "SELECT * FROM Usuarios";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                User user = new User();

                user.setName(rs.getString("nombre"));
                user.setLastname(rs.getString("apellido"));
                user.setUsername(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("rol"));
                user.setFcoins(rs.getString("fcoins"));

                // Creating a new UserApp class instance and adding it to the array list
                users.add(user);
            }

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return Optional.of(users);
    }

    public Optional<User> createUser(String name, String lastname,String username,String password,String role,String fcoin,String path) throws IOException {

        String newLine = "\n"+name+ "," +lastname+ "," +username + "," + password + "," +role+ "," +fcoin;
        FileOutputStream os = new FileOutputStream(path, true);
        os.write(newLine.getBytes());
        os.close();
        User newuser = new User();
        newuser.setName(name);
        newuser.setLastname(lastname);
        newuser.setUsername(username);
        newuser.setPassword(password);
        newuser.setRole(role);
        newuser.setFcoins(fcoin);
        return Optional.of(newuser);
    }

    public Optional<Boolean> loadFcoins(String username,String Fcoins, boolean compra){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            List<User> users = getUsers(conn).get();

            User user1 = users.stream().filter(user-> username.equals(user.getUsername())).findFirst().get();
            int numFcoins = Integer.parseInt(Fcoins);
            int numUser = Integer.parseInt(user1.getFcoins());
            int newFcoins;
            if (compra){
                int aux = numUser-numFcoins;
                if (aux<0){
                    return Optional.of(false);
                }else {
                    newFcoins = aux;
                }
            }else {
                newFcoins = numUser+numFcoins;
            }

            String sql = "UPDATE Usuarios SET fcoins = ? WHERE usuario = '"+user1.getUsername()+"'";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newFcoins);
            stmt.executeUpdate();

            stmt.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.of(false);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.of(false);
        } catch (SQLException se) {
            se.printStackTrace();
            return Optional.of(false);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return Optional.of(false);
        }finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Optional.of(true);
    }
}
