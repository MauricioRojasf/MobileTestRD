package com.example.rockhopper.mobiletest;

import java.io.Serializable;

/**
 * Created by rockhopper on 19-12-17.
 */

public class Feed implements Serializable{
    private String title;
    private String author;
    private String url;
    private String date;
    private int deleted;

    public Feed (String title, String author, String url, String date){
        this.title = title;
        this.author = author;
        this.url = url;
        this.date  = date;

    }

    public String getTitle(){return title;}

    public void setTitle(String title){this.title = title;}

    public String getAuthor(){return author;}

    public void  setAuthor(String author){this.author = author;}

    public String getUrl(){return url;}

    public void setUrl(String url){this.url = url;}

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

    public int getDeleted(){return deleted;}

    public void setDeleted(int deleted){this.deleted = deleted;}
}
