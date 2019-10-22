package com.takhir.rssreader.models;

public class Post {

    private String title;
    private String desc;
    private String date;
    private String image;

    public Post(String title, String desc, String date, String image) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
