package com.example.myapplication.model;

/**
 * Created by xieH on 2016/12/5 0005.
 */
public class Message {

    public final static int TYPE_SEND = 0;
    public final static int TYPE_RECEIVED = 1;


    private int id;

    private int type;

    private String title;

    private boolean isTextMessage = false;
    private boolean isImageMessage = false;
    private boolean isRedEnvelopesMessage = false;

    //////////////
    private int percent;
    private String content;
    private int color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTextMessage() {
        return isTextMessage;
    }

    public void setTextMessage(boolean textMessage) {
        isTextMessage = textMessage;
    }

    public boolean isImageMessage() {
        return isImageMessage;
    }

    public void setImageMessage(boolean imageMessage) {
        isImageMessage = imageMessage;
    }

    public boolean isRedEnvelopesMessage() {
        return isRedEnvelopesMessage;
    }

    public void setRedEnvelopesMessage(boolean redEnvelopesMessage) {
        isRedEnvelopesMessage = redEnvelopesMessage;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
