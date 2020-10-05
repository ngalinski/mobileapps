package edu.neu.madcourse.numad20f_nicholasgalinski;

public class LinkItem {
    private String linkName;
    private String linkURL;

    public LinkItem(String linkName, String linkURL) {
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public String getName() {
        return linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setName() {
        this.linkName = linkName;
    }

    public void setLinkURL() {
        this.linkURL = linkURL;
    }
}
