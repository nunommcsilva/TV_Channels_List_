package com.example.admin.NowAndNext;

public class Channel {

    private String title;
    private CurrentProgram currentProgram;

    public Channel() {
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

    public void setCurrentProgram(CurrentProgram currentProgram) {
        this.currentProgram = currentProgram;
    }
}

