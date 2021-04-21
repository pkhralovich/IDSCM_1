package com.upc.idscm.models;

import com.upc.idscm.tools.Database;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Video {
    private String title;
    private String author;
    private Date creation_date;
    private String duration;
    private long plays;
    private String description;
    private int user_id;
    private String format;
    private String path;
    
    public Video(String title, String author, Date creation_date,
                    String duration, long plays, String description, int user_id,
                    String format, String path) {
        this.title = title;
        this.author = author;
        this.creation_date = creation_date;
        this.duration = duration;
        this.plays = plays;
        this.description = description;
        this.user_id = user_id;
        this.format = format;
        this.path = path;
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
    
    public long getPlays() {
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
    
    public String getPath() {
        return this.path;
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
    
    public void setPath(String path) {
        this.path = path;
    }
    
    private static final String QUERY_USER_VIDEOS = "SELECT * FROM videos WHERE user_id = ?";
    private static final String QUERY_TITLE_USED = "SELECT * FROM videos WHERE title = ? AND user_id = ?";
    private static final String QUERY_INSERT = "INSERT INTO Videos (title, author, creation_date, duration, plays, description, format, user_id, path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public class MAX_LENGTH {
        public static final int TITLE = 45;
        public static final int AUTHOR = 45;
        public static final int DURATION = 45;
        public static final long PLAYS = Long.MAX_VALUE;
        public static final int DESCRIPTION = 45;
        public static final int FORMAT = 45;
        public static final int PATH = 300;
    }
    
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
        public static final String PATH = "path";
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
            String path = result.getString(FIELDS.PATH);
            
            oRes.add(new Video(title, author, creation_date, duration, plays, description, user_id, format, path));
        }
        
        return oRes;
    }
    
    public static boolean titleInUse(String title, int user) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_TITLE_USED);
        statement.setString(1, title);
        statement.setInt(2, user);
        
        ResultSet result = statement.executeQuery();
        return result.next();
    }
    
    public static boolean insert(Video video) throws SQLException {
        PreparedStatement statement = Database.instance().connection.prepareStatement(QUERY_INSERT);
        statement.setString(1, video.getTitle());
        statement.setString(2, video.getAuthor());
        statement.setDate(3, new java.sql.Date(video.getCreationDate().getTime()));
        statement.setString(4, video.getDuration());
        statement.setLong(5, video.getPlays());
        statement.setString(6, video.getDescription());
        statement.setString(7, video.getFormat());
        statement.setInt(8, video.getUser());
        statement.setString(9, video.getPath());
        
        return statement.executeUpdate() > 0;
    }
    
}
