package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/2.
 */

public class VideoInfo {
    private int showapi_res_code;
    private String showapi_res_error;
    private ResponseBody showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ResponseBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ResponseBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public class ResponseBody {
        private int ret_code;
        private VideoResponse pagebean;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public VideoResponse getPagebean() {
            return pagebean;
        }

        public void setPagebean(VideoResponse pagebean) {
            this.pagebean = pagebean;
        }
    }

    public class VideoResponse{
        private int allPages;
        private ArrayList<VideoBean> contentlist;
        private int currentPage;
        private int allNum;
        private int maxResult;

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public ArrayList<VideoBean> getContentlist() {
            return contentlist;
        }

        public void setContentlist(ArrayList<VideoBean> contentlist) {
            this.contentlist = contentlist;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }
    }
}
