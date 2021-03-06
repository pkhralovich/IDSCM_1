/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginMCV.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author CristianMatas
 */
public class Consultas extends Connexion{
    
    public boolean Authentication(String user, String pass) throws SQLException
    {
        Statement st = con.createStatement();
        ResultSet rs = null;
        String Consulta = "Select username, password from users WHERE username = '" + user + "'";
        rs = st.executeQuery(Consulta);
        
        //miro si la combinación username y pwd es correcta segun mi DB
        if(user.equals(rs.getString("username")) && pass.equals((rs.getString("password")))) 
            return true;
        
        return false;
    }
    
    
    //función para testear que funciona
    public static void main(String[] args) {
        try {
            Consultas con = new Consultas();
            System.out.println(con.Authentication("cmatas", "prueba123"));
        } catch (Exception e) {
            //TODO: Tratar excepcion
        }
        
    }
}
