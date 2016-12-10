package com.yh.mohudaily.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 */

public class TopStoryBean implements Serializable{


    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    public String getImages() {
        return image;
    }

    public void setImages(String images) {
        this.image = images;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "StoryBean{" +
                "images=" + image +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
