package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 */
public class BeforeNewsInfo {
    private String date;

    private ArrayList<StoryBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<StoryBean> getStories() {
        return stories;
    }

    public void setStories(ArrayList<StoryBean> stories) {
        this.stories = stories;
    }

}
