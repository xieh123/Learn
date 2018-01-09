package com.example.myapplication.model;

/**
 * Created by xieH on 2016/12/3 0003.
 */
public class Item {

    private String title;

    private String text;

    private int resourceId;

    private String url;

    private boolean isSelected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", resourceId=" + resourceId +
                ", url='" + url + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
