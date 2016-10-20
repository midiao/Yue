package com.superjunior.yue.model;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class NewsBean {
    private String title;
    private String data;
    private String url;
    private String type;
    private String realtype;
    private String author_name;

    public NewsBean(String title, String data, String url, String type, String realtype, String author_name) {
        this.title = title;
        this.data = data;
        this.url = url;
        this.type = type;
        this.realtype = realtype;
        this.author_name = author_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRealtype() {
        return realtype;
    }

    public void setRealtype(String realtype) {
        this.realtype = realtype;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}
