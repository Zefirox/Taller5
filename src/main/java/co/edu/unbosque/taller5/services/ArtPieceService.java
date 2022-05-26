package co.edu.unbosque.taller5.services;

import co.edu.unbosque.taller5.dtos.ArtPiece;
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

public class ArtPieceService {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/taller5";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "Zeref29714526?";

    public Optional<List<ArtPiece>> getArtPiece() throws IOException{

        Connection connection = null;

        // Object for handling SQL statement
        Statement stmt1 = null;
        PreparedStatement prep = null;

        // Data structure to map results from database
        List<ArtPiece> artPieces = new ArrayList<ArtPiece>();

        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Executing a SQL query
            stmt1 = connection.createStatement();
            String sql1 = "SELECT * FROM Arte";
            String sql2 = "SELECT nombre FROM Colecciones x WHERE x.coleccion_id = ?";
            prep = connection.prepareStatement(sql2);
            ResultSet rs1 = stmt1.executeQuery(sql1);

            // Reading data from result set row by row
            while (rs1.next()) {
                // Extracting row values by column name
                ArtPiece art = new ArtPiece();

                art.setUsernamecreator(rs1.getString("usuario"));
                art.setTitle(rs1.getString("titulo"));
                art.setFilename(rs1.getString("nombrearchivo"));
                art.setPrice(rs1.getString("precio"));
                prep.setInt(1, rs1.getInt("coleccion_id"));
                ResultSet rs2 = prep.executeQuery();
                rs2.next();
                art.setCollection(rs2.getString("nombre"));

                // Creating a new UserApp class instance and adding it to the array list
                artPieces.add(art);
                rs2.close();
            }

            // Closing resources
            rs1.close();
            stmt1.close();
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            // Cleaning-up environment
            try {
                if (stmt1 != null) stmt1.close();
                if (prep != null) prep.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return Optional.of(artPieces);
    }

    public Optional<Boolean> createArt(String username, String title, String price, String filename, String currentCollection, String path) throws IOException {
        try{
            String newLine = username+ "," +title+ "," +price + "," + filename+ "," +currentCollection+"\n";
            FileOutputStream os = new FileOutputStream(path, true);
            os.write(newLine.getBytes());
            os.close();
            return Optional.of(true);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.of(false);
        }
    }
}
