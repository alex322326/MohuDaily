package com.yh.mohudaily.entity;

/**
 * Created by YH on 2016/12/2.
 */

public class BeautyImageBean {
    public BeautyImageBean() {
    }

    private String id;
    private String title;
    private String link;
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "BeautyImageBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
