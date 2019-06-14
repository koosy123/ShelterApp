package com.cookandroid.shelterapp;

public class newsItem {
    private String title;
    private String content;
    private String newsurl;

    public void setTitle(String t) {
        title = t ;
    }
    public void setContent(String c) {
        content = c;
    }
    public void setNewsurl(String n) {
        newsurl = n;
    }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public String getNewsurl(){
        return this.newsurl;
    }
}
