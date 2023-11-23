package main;

import fileio.input.SongInput;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private String owner;
    private boolean isPublic = true;
    private ArrayList<SongInput> songs;
    private int followers = 0;
    private Integer duration;
    private Integer playedForTime = 0;

    public Integer getPlayedForTime() {
        return playedForTime;
    }

    public void setPlayedForTime(Integer playedForTime) {
        this.playedForTime = playedForTime;
    }

    public int getFollowers() {
        return followers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void addSong(SongInput song) {
        songs.add(song);
    }

    public void removeSong(SongInput song) {
        songs.remove(song);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
