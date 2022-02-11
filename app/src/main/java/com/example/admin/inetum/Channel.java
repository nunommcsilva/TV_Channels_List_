package com.example.admin.inetum;

import org.json.JSONObject;

public class Channel extends JSONObject {

    private String title;
    private CurrentProgram currentProgram;

    public Channel() {
    }

    public Channel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CurrentProgram getCurrentProgram() {
        return currentProgram;
    }

}

