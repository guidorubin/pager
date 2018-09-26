package com.pager.pagerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusChangeNotification {

    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("state")
    @Expose
    private String state;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
