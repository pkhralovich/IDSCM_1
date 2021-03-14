package com.upc.idscm.database;

import static com.upc.idscm.database.Database.m_connection;
import com.upc.idscm.models.Video;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoDB extends Database {
    private static final String QUERY_USER_VIDEOS = "SELECT * FROM videos WHERE user_id = ?";
    
    public class FIELDS {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String DATE = "creation_date";
        public static final String DURATION = "duration";
        public static final String PLAYS = "plays";
        public static final String DESCRIPTION = "description";
        public static final String USERID = "user_id";
        public static final String FORMAT = "format";
    }
    
    //INSTANCIA DE PATRON SINGLETON
    private static VideoDB m_instance;
    
    private VideoDB() {
        super();
    }
    
    public static VideoDB instance() {
        if (m_instance == null)
            m_instance = new VideoDB();
        return m_instance;
    }
    
    public List<Video> getUserVideos(int user) throws SQLException {
        PreparedStatement statement = m_connection.prepareStatement(QUERY_USER_VIDEOS);
        statement.setInt(1, user);
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Video> oRes = new ArrayList<Video>();
        while (result.next()) {
            int id = result.getInt(FIELDS.ID);
            String title = result.getString(FIELDS.TITLE);
            String author = result.getString(FIELDS.AUTHOR);
            Date creation_date = result.getDate(FIELDS.DATE);
            String duration = result.getString(FIELDS.DURATION);
            int plays = result.getInt(FIELDS.PLAYS);
            String description = result.getString(FIELDS.DESCRIPTION);
            int user_id = result.getInt(FIELDS.USERID);
            String format = result.getString(FIELDS.FORMAT);
            
            oRes.add(new Video(id, title, author, creation_date, duration, plays, description, user_id, format));
        }
        
        return oRes;
    }
}
