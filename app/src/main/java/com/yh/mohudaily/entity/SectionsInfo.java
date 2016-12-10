package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 */

public class SectionsInfo {
    private ArrayList<Section> data;

    public ArrayList<Section> getData() {
        return data;
    }

    public void setData(ArrayList<Section> data) {
        this.data = data;
    }

    public static class Section{
        private String description;
        private int id;
        private String name;
        private String thumbnail;

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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "description='" + description + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }
    }
}
