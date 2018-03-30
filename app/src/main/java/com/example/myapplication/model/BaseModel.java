package com.example.myapplication.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class BaseModel implements Serializable {
    public String getMoneyStr(int num) {
        return "¥" + getRealMoney(num);
    }

    public String getRealMoney(int num) {
        return new DecimalFormat("###################.###########").format((((double) num) * 1.0d) / 100.0d);
    }
}
