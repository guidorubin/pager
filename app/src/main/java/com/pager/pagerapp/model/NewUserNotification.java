package com.pager.pagerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewUserNotification {

    @Expose
    @SerializedName("event")
    private String event;

    @Expose
    @SerializedName("user")
    private Member member;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
