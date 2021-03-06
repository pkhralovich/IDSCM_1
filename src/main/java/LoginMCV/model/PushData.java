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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PushData extends Connexion{
    public String Add(String name, String surn, String email, String uname, String pwd) throws SQLException {
        int i = 0;
        if (uname != null) {
            Statement st = con.createStatement();
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                if (con != null) {
                    String sql = "INSERT INTO users(id, name, surname, email, username, password) VALUES(default,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, surn);
                    ps.setString(3, email);
                    ps.setString(4, uname);
                    ps.setString(5, pwd);
                    i = ps.executeUpdate();
                    System.out.println("Data Added Successfully");
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    con.close();
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (i > 0) {
            return "success";
        } else
            return "unsuccess";
    }
    
        //función para testear que funciona
    public static void main(String[] args) {
        try {
            PushData con = new PushData();
            System.out.println(con.Add("Albert", "Barcelo", "albertbarcelocasas@gmail.com", "Rulemule", "YamelacreoYO"));
        } catch (Exception e) {
            //TODO: Tratar excepcion
        }
        
    }
}
