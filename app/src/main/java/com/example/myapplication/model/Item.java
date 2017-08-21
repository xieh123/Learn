package com.example.myapplication.model;

/**
 * Created by xieH on 2016/12/3 0003.
 */
public class Item {

    private String title;

    private int resourceId;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", resourceId=" + resourceId +
                ", url='" + url + '\'' +
                '}';
    }
}
