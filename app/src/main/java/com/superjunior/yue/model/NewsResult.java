package com.superjunior.yue.model;

import java.util.List;

/**
 * Created by cb8695 on 2016/10/28.
 */

public class NewsResult {

    /**
     * reason : 成功的返回
     * result : {}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private int stat;
        private List<NewsBean> data;

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }

        public List<NewsBean> getData() {
            return data;
        }

        public void setData(List<NewsBean> data) {
            this.data = data;
        }

    }
}
