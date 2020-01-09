package com.example.appone;

public class Track {
    private String trackid;
    private String trackname;
    private int rating;

    public Track(){

    }

    public Track(String trackid, String trackname, int rating) {
        this.trackid = trackid;
        this.trackname = trackname;
        this.rating = rating;
    }

    public String getTrackidId() {
        return trackid;
    }

    public String getTrackname() {
        return trackname;
    }

    public int getRating() {
        return rating;
    }
}
