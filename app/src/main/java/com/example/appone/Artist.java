package com.example.appone;

public class Artist {

    String artistId;
    String artistName;
    String artistGenere;

    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenere) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenere = artistGenere;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenere() {
        return artistGenere;
    }
}
