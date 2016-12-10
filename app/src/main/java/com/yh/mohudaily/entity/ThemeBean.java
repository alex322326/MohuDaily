package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 */
public class ThemeBean {
    @Override
    public String toString() {
        return "ThemeBean{" +
                "stories=" + stories +
                ", description='" + description + '\'' +
                ", background='" + background + '\'' +
                ", color=" + color +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", editors=" + editors +
                ", image_source='" + image_source + '\'' +
                '}';
    }

    private ArrayList<StoryBean> stories;
    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private ArrayList<Editor> editors;
    private String image_source;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<StoryBean> getStories() {
        return stories;
    }

    public void setStories(ArrayList<StoryBean> stories) {
        this.stories = stories;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Editor> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<Editor> editors) {
        this.editors = editors;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }


    static class Editor{
        private String url;
        private String bio;
        private int id;
        private String avatar;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
