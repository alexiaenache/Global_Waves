package main;

import java.util.ArrayList;

public class Filters {
    private String name;
    private String album;
    private String owner;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;

    private ArrayList<String> tags;

    public int countInitializedFildes() {
        int count = 0;
        if(this.name != null) {
            count++;
        }
        if(this.album != null) {
            count++;
        }
        if(this.owner != null) {
            count++;
        }
        if(this.lyrics != null) {
            count++;
        }
        if(this.genre != null) {
            count++;
        }
        if(this.releaseYear != null) {
            count++;
        }
        if(this.artist != null) {
            count++;
        }
        if(this.tags != null) {
            count++;
        }
        return count;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
