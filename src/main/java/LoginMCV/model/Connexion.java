/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginMCV.model;

/**
 *
 * @author CristianMatas
 */

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    
    public static final String USERNAME = "root";
    public static final String PASSWORD = "crm9";
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DATABASE = "idscm";
    public static final String CLASSNAME = "com.myswl.jdbc.Driver";
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + "DATABASE";
    
    public java.sql.Connection con;
    
        public Connexion() {
            try {
                Class.forName(CLASSNAME);
                con = DriverManager.gerConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.out.println("Error");
            } catch (SQLException e) {
                System.out.println("Error");
            }
            
        }
    
}
