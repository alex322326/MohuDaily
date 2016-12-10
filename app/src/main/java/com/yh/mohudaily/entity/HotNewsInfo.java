package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 */

public class HotNewsInfo {

    private ArrayList<Hot> recent;

    public ArrayList<Hot> getRecent() {
        return recent;
    }

    public void setRecent(ArrayList<Hot> recent) {
        this.recent = recent;
    }

    public static class Hot{
        private int news_id;
        private String url;
        private String thumbnail;
        private String title;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Hot{" +
                    "news_id=" + news_id +
                    ", url='" + url + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
