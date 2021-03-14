package com.upc.idscm.database;

import com.upc.idscm.models.User;
import com.upc.idscm.tools.Encryption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends Database {
    private static final String QUERY_AUTH = "SELECT username, password FROM users WHERE username = ? AND password = ?";
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
    
    //INSTANCIA DE PATRON SINGLETON
    private static UserDB m_instance;
    
    private UserDB() {
        super();
    }
    
    public static UserDB instance() {
        if (m_instance == null)
            m_instance = new UserDB();
        return m_instance;
    }
    
    public boolean Authenticate(String user, String pass) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_AUTH);
        statement.setString(1, user);
        statement.setString(2, Encryption.hash(pass));
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
    
    public boolean Signup(User user) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_SIGNUP);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getUsername());
        statement.setString(5, Encryption.hash(user.getPassword()));
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean Drop(String username) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_DROP);
        statement.setString(1, username);
        
        return statement.executeUpdate() > 0;
    }
    
    public boolean usernameUsed(String username) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_SELECT_BY_USERNAME);
        statement.setString(1, username);
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
    
    public boolean emailInUse(String email) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_SELECT_BY_EMAIL);
        statement.setString(1, email);
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
    
    public static void main(String[] args) {
        try {
            final String MOCK_USER = "pavelK96";
            final String MOCK_PASS = "1234";
            
            boolean droped = UserDB.instance().Drop(MOCK_USER);
            System.out.println("Droped: " + droped);
            
            boolean authenticated = UserDB.instance().Authenticate(MOCK_USER, MOCK_PASS);
            System.out.println("Authenticated 1: " + authenticated);
            
            User user = new User("pavel", "Khralovich", "pavel.khralovich@gmail.com",
                                    MOCK_USER, MOCK_PASS);
            boolean created = UserDB.instance().Signup(user);
            System.out.println("Created: " + created);
            
            authenticated = UserDB.instance().Authenticate(MOCK_USER, MOCK_PASS);
            System.out.println("Authenticated 2: " + authenticated);
            
            boolean exist = UserDB.instance().emailInUse("pavel@logic-key.com");
            System.out.println("Mail in use: " + exist);
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
