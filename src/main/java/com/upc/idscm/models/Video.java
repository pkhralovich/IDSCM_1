package com.upc.idscm.models;

import com.upc.idscm.tools.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Video {
    private int id;
    
    private String title;
    private String author;
    private Date creation_date;
    private String duration;
    private int plays;
    private String description;
    private int user_id;
    private String format;
    
    public Video(int id, String title, String author, Date creation_date,
                    String duration, int plays, String description, int user_id,
                    String format) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creation_date = creation_date;
        this.duration = duration;
        this.plays = plays;
        this.description = description;
        this.user_id = user_id;
        this.format = format;
    }
    
    public int getId() {
        return this.id;
    }
        
    public String getTitle() {
        return this.title;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public Date getCreationDate() {
        return this.creation_date;
    }
    
    public String getDuration() {
        return this.duration;
    }
    
    public int getPlays() {
        return this.plays;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getUser() {
        return this.user_id;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void setId(int value) {
        this.id = value;
    }
    
    public void setTitle(String value) {
        this.title = value;
    }
    
    public void setAuthor(String value) {
        this.author = value;
    }
    
    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public void setPlays(int plays) {
        this.plays = plays;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
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
    
    public static List<Video> getUserVideos(int user) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_USER_VIDEOS);
        statement.setInt(1, user);
        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Video> oRes = new ArrayList<>();
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
