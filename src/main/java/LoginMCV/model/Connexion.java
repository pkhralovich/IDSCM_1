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

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    
    public static final String USERNAME = "root";
    public static final String PASSWORD = "crm9";
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DATABASE = "idscm";
    public static final String CLASSNAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + "DATABASE";
    
    public java.sql.Connection con;
    
        public Connexion() {
            try {
                Class.forName(CLASSNAME);
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("SUCCESS");
            } catch (ClassNotFoundException e) {
                System.out.println("Error 1: " + e.getLocalizedMessage());
            } catch (Exception e) {
                System.out.println("Error 2: " + e.getLocalizedMessage());
            }
            
        }
        
        //TEST PARA VER SI CONECTA
        public static void main(String[] args){
            Connexion con = new Connexion();
        }
    
}
