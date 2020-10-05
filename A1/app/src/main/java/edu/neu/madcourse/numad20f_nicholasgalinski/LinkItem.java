package edu.neu.madcourse.numad20f_nicholasgalinski;

public class LinkItem {
    private String linkName;
    private String linkURL;
    private boolean isChecked;

    public LinkItem(String linkName, String linkURL, boolean isChecked) {
        this.linkName = linkName;
        this.linkURL = linkURL;
        this.isChecked = isChecked;
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

    public boolean getStatus() {
        return isChecked;
    }

    public void onCardClicked(boolean misChecked, String name) {
        isChecked = misChecked;
        if (isChecked) {
            linkName = name;
        }
        else {
            linkName = "Example item";
        }
    }

    public void onCheckboxClicked(boolean misChecked, String name) {
        isChecked = misChecked;
        if (isChecked) {
            linkName = name;
        }
        else {
            linkName = "Example item";
        }
    }
}
