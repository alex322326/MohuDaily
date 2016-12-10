package com.yh.mohudaily.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 */
public class ThemesBean {
    private int limit;

    private ArrayList<Theme> subscribed;

    private ArrayList<Theme> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ArrayList<Theme> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(ArrayList<Theme> subscribed) {
        this.subscribed = subscribed;
    }

    public ArrayList<Theme> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<Theme> others) {
        this.others = others;
    }

    public static class Theme implements Serializable{
        private int color;

        private String thumbnail;

        private String description;

        private int id;

        private String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "color=" + color +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", description='" + description + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
