package com.upc.idscm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database {
    //PARAMETROS DE CONEXION
    private static final String USERNAME = "b1618172c3c467";
    private static final String PASSWORD = "bc888a24";
    private static final String HOST = "eu-cdbr-west-03.cleardb.net";
    private static final String PORT = "3306";
    private static final String DATABASE = "heroku_bc706673f184f21";
    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true&useSSL=false";
    
    //CONEXION A LA BBDD
    protected static Connection m_connection;
    
    //INSTANCIA DE PATRON SINGLETON
    //private static Database m_instance;
    
    //CONSTRUCTOR PRIVADO
    protected Database() {
        try {
            Class.forName(CLASSNAME);
            m_connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL connection class not found");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
