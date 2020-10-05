package edu.neu.madcourse.numad20f_nicholasgalinski;

public class LinkItem {
    private String name;
    private String linkURL;

    public LinkItem(String name, String linkURL) {
        this.name = name;
        this.linkURL = linkURL;
    }

    public String getName() {
        return name;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setName() {
        this.name = name;
    }

    public void setLinkURL() {
        this.linkURL = linkURL;
    }
}
