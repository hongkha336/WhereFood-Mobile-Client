package com.wtf.wherefood.Network;

public class EventModel {
    private String eventTag;
    private String message;

    public EventModel(String eventTag, String message) {
        this.eventTag = eventTag;
        this.message = message;
    }

    public boolean isTagMatchWith(String tag){
        return eventTag.equals(tag);
    }

    public String getMessage() {
        return message;
    }
}