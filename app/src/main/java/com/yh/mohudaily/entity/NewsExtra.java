package com.yh.mohudaily.entity;

import java.io.Serializable;

/**
 * Created by YH on 2016/11/15.
 */
public class NewsExtra implements Serializable{
    private String long_comments;
    private String popularity;
    private String short_comments;
    private String comments;

    public String getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(String long_comments) {
        this.long_comments = long_comments;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(String short_comments) {
        this.short_comments = short_comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "NewsExtra{" +
                "long_comments=" + long_comments +
                ", popularity=" + popularity +
                ", short_comments=" + short_comments +
                ", comments=" + comments +
                '}';
    }
}
