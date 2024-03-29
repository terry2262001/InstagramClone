package com.example.instagram.Model;

public class Story {
    private String imageurl;
    private long timestart;
    private long timeend;
    private String storyid;
    private String userid;

    public Story(String imageurl, long timestart, long timeend, String storyid, String userid) {
        this.imageurl = imageurl;
        this.timestart = timestart;
        this.timeend = timeend;
        this.storyid = storyid;
        this.userid = userid;
    }

    public Story() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Story) {
            Story another = (Story) obj;
            if (this.userid.equals(another.userid)) {

                return true;
            }
        }
        return false;
    }
    public int hashCode() {
        return 31 + userid.hashCode();
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getTimestart() {
        return timestart;
    }

    public void setTimestart(long timestart) {
        this.timestart = timestart;
    }

    public long getTimeend() {
        return timeend;
    }

    public void setTimeend(long timeend) {
        this.timeend = timeend;
    }

    public String getStoryid() {
        return storyid;
    }

    public void setStoryid(String storyid) {
        this.storyid = storyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Story{" +
                "imageurl='" + imageurl + '\'' +
                ", timestart=" + timestart +
                ", timeend=" + timeend +
                ", storyid='" + storyid + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
