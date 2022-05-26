package co.edu.unbosque.taller5.resources;

import co.edu.unbosque.taller5.dtos.User;
import co.edu.unbosque.taller5.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UserResource {
    @Context
    ServletContext context;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/taller5";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "Zeref29714526?";

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@FormParam("name") String name,
                               @FormParam("lastname") String lastname,
                               @FormParam("username") String username,
                               @FormParam("password") String password,
                               @FormParam("role") String role) {

        String fcoins = "0";

        String path = context.getRealPath("") + File.separator + "WEB-INF/classes/" + "accounts.csv";
        User signup = null;
        try {
            signup = new UserService().createUser(name, lastname, username, password, role, fcoins, path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (signup != null) {
            return Response.ok().entity(signup).build();
        }

        return Response.serverError().build();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username,
                            @QueryParam("password")String password) {

        Connection conn = null;
        User userfound = null;

        try {

            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            List<User> users = new UserService().getUsers(conn).get();

            userfound = users.stream().filter(user -> username.equals(user.getUsername()) && password.equals(user.getPassword())).findFirst().orElse(null);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        if (userfound != null) {
            return Response.ok().entity(userfound).build();
        } else {
            return Response.status(404).build();
        }
    }

    @PUT
    @Path("/{username}/fcoins")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFcoins(@PathParam("username") String username,
                                  @FormParam("fcoins") String fcoins) {

        boolean update = new UserService().loadFcoins(username,fcoins,false).get();

        Connection conn = null;

        if(update){
            List<User> users = null;
            try {
                // Registering the JDBC driver
                Class.forName(JDBC_DRIVER);

                // Opening database connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                users = new UserService().getUsers(conn).get();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                // Cleaning-up environment
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            User userfound = users.stream().filter(user -> username.equals(user.getUsername())).findFirst().orElse(null);
            if(userfound!=null){
                return Response.ok().entity(userfound).build();
            }
        }

        return Response.serverError().build();
    }

    @PUT
    @Path("/buy")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyArt(@FormParam("userBuyer") String userBuyer,
                                  @FormParam("price") String price) {

        boolean update = new UserService().loadFcoins(userBuyer,price,true).get();

        Connection conn = null;

        if(update){
            List<User> users = null;
            try {
                // Registering the JDBC driver
                Class.forName(JDBC_DRIVER);

                // Opening database connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                users = new UserService().getUsers(conn).get();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                // Cleaning-up environment
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

            User userfound = users.stream().filter(user -> userBuyer.equals(user.getUsername())).findFirst().orElse(null);
            if(userfound!=null){
                return Response.ok().entity(userfound).build();
            }
        }
        return Response.serverError().build();
    }
}