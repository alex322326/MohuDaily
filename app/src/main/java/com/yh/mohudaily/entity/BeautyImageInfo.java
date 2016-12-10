package com.yh.mohudaily.entity;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/2.
 */

public class BeautyImageInfo {
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
        private ImageResponse pagebean;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public ImageResponse getPagebean() {
            return pagebean;
        }

        public void setPagebean(ImageResponse pagebean) {
            this.pagebean = pagebean;
        }
    }

    public class ImageResponse{
        private String allPages;
        private ArrayList<BeautyImageBean> contentlist;
        private String currentPage;
        private String allNum;
        private String maxResult;

        public String getAllPages() {
            return allPages;
        }

        public void setAllPages(String allPages) {
            this.allPages = allPages;
        }

        public ArrayList<BeautyImageBean> getContentlist() {
            return contentlist;
        }

        public void setContentlist(ArrayList<BeautyImageBean> contentlist) {
            this.contentlist = contentlist;
        }

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public String getAllNum() {
            return allNum;
        }

        public void setAllNum(String allNum) {
            this.allNum = allNum;
        }

        public String getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(String maxResult) {
            this.maxResult = maxResult;
        }
    }
}
