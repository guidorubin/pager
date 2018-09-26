package com.pager.pagerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @Expose
    @SerializedName("event")
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    public enum EventType {
        STATE_CHANGE("state_change"),
        NEW_USER("user_new");

        private final String text;

        EventType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }


}
