package co.edu.unbosque.taller5.dtos;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String lastname;

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String password;

    @CsvBindByName
    private String role;

    @CsvBindByName
    private String fcoins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFcoins() {
        return fcoins;
    }

    public void setFcoins(String fcoins) {
        this.fcoins = fcoins;
    }
}
