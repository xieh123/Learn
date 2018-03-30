package com.example.myapplication.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class Answer extends BaseModel {
    private int answerTime;
    private int correctOption = -1;
    private String created;
    private long delayTime;
    private String desc;
    private int displayOrder;
    private int liveId;
    private List<String> optionList;
    private String options;
    private int questionId;
    private long showTime;
    private List<Integer> stats;
    private String updated;
    private int used;
    private int userAnswerDoStatus = -1;
    private int userSelected = -1;

    public int getAnswerTime() {
        return this.answerTime;
    }

    public int getCorrectOption() {
        return this.correctOption;
    }

    public String getCreated() {
        return this.created;
    }

    public long getDelayTime() {
        return this.delayTime;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getDisplayOrder() {
        return this.displayOrder;
    }

    public int getLiveId() {
        return this.liveId;
    }

    public List<String> getOptionList() {
        if ((this.optionList == null || this.optionList.size() == 0) && !TextUtils.isEmpty(this.options)) {
            setOptionList(JSON.parseArray(this.options, String.class));
        }
        return this.optionList;
    }

    public String getOptions() {
        return this.options;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public long getShowTime() {
        return this.showTime;
    }

    public List<Integer> getStats() {
        return this.stats;
    }

    public String getUpdated() {
        return this.updated;
    }

    public int getUsed() {
        return this.used;
    }

    public int getUserAnswerDoStatus() {
        return this.userAnswerDoStatus;
    }

    public int getUserSelected() {
        return this.userSelected;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public void setOptionList(List<String> list) {
        this.optionList = list;
    }

    public void setOptions(String options) {
        if (!TextUtils.isEmpty(options)) {
            setOptionList(JSON.parseArray(options, String.class));
        }
        this.options = options;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public void setStats(List<Integer> list) {
        this.stats = list;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public void setUserAnswerDoStatus(int userAnswerDoStatus) {
        this.userAnswerDoStatus = userAnswerDoStatus;
    }

    public void setUserSelected(int userSelected) {
        this.userSelected = userSelected;
    }
}
