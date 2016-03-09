package com.hugh.bmobtest;

import cn.bmob.v3.BmobObject;

/**
 * Created by hugh on 2016/3/8.
 */
public class FeedBack extends BmobObject {
    private String name;
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
