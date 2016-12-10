package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/15.
 */

public class CommentInfo {
    private ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment{
        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", time=" + time +
                    ", id=" + id +
                    ", likes=" + likes +
                    '}';
        }
    }
}
