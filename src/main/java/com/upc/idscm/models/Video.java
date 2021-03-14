package com.upc.idscm.models;

import java.util.Date;

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
}
