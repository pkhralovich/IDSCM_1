package com.upc.idscm.models;

import com.upc.idscm.tools.Database;
import com.upc.idscm.tools.Encryption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;

    public User() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.username = "";
        this.password = "";
    }
    
    public User(String name, String surname, String email, 
                String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username; 
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    private static final String QUERY_AUTH = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String QUERY_SIGNUP = "INSERT INTO users (name, surname, email, username, password) VALUES (?, ?, ?, ?, ?)";
    private static final String QUERY_DROP = "DELETE FROM users WHERE username = ?";
    private static final String QUERY_SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    
    public class MAX_LENGTH {
        public static final int USERNAME = 45;
        public static final int PASSWORD = 20;
        public static final int EMAIL = 45;
        public static final int NAME = 45;
        public static final int SURNAME = 45;
    }
    
    public class FIELDS {
        public static final String ID = "id";
    }  
    
    public static int authenticate(String user, String pass) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_AUTH);
        statement.setString(1, user);
        statement.setString(2, Encryption.hash(pass));
        
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getInt(FIELDS.ID);
        } else {
            return -1;
        }
    }
    
    public static boolean signup(User user) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_SIGNUP);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getUsername());
        statement.setString(5, Encryption.hash(user.getPassword()));
        
        return statement.executeUpdate() > 0;
    }
    
    public static boolean drop(String username) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_DROP);
        statement.setString(1, username);
        
        return statement.executeUpdate() > 0;
    }
    
    public static boolean usernameUsed(String username) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_SELECT_BY_USERNAME);
        statement.setString(1, username);
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
    
    public static boolean emailInUse(String email) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_SELECT_BY_EMAIL);
        statement.setString(1, email);
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
}
