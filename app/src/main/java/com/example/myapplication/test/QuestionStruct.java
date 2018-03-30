package com.example.myapplication.test;

import java.util.List;

/**
 * Created by xieH on 2018/1/16 0016.
 */
public class QuestionStruct {

    public long activityId;
    public long commitDelay;
    public String[] imageUrl;
    public OptionStruct[] options;
    public long questionId;
    public long questionStartTsMs;
    public int random;
    public String text;
    public long timeLimit;
    public long uuQuestionId;
    public String[] videoUrl;

    public List<OptionStruct> optionsList;
}
