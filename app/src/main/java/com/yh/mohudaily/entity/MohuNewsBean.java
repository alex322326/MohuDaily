package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/8.
 */
public class MohuNewsBean {
    private String date;

    private ArrayList<StoryBean> stories;

    private ArrayList<TopStoryBean> top_stories;
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

    public ArrayList<TopStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<TopStoryBean> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public String toString() {
        return "MohuNewsBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }



}
