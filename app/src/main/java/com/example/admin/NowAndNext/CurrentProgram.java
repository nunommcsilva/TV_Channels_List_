package com.example.admin.NowAndNext;

public class CurrentProgram {

    private String programTitle;
    private String nextProgramTitle;
    private String coverImageURL;

    public CurrentProgram() {
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getNextProgramTitle() {
        return nextProgramTitle;
    }

    public void setNextProgramTitle(String nextProgramTitle) {
        this.nextProgramTitle = nextProgramTitle;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    @Override
    public String toString() {
        return "CurrentProgram{" +
                "programTitle='" + programTitle + '\'' +
                ", coverImageURL='" + coverImageURL + '\'' +
                '}';
    }
}

