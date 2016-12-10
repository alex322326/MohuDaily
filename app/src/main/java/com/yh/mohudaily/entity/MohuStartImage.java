package com.yh.mohudaily.entity;

/**
 * Created by YH on 2016/11/9.
 */
public class MohuStartImage {
    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "MohuStartImage{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
