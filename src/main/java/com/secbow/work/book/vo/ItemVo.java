package com.secbow.work.book.vo;

public class ItemVo {
    private int res;
    private String url;
    private String title;
    private String describe;

    public ItemVo(int res, String url, String title, String describe) {
        this.res = res;
        this.url = url;
        this.title = title;
        this.describe = describe;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
