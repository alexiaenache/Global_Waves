package main;

import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the information about a playlist
 */
public class Playlist {
    private String name;
    private String owner;
    private boolean isPublic = true;
    private ArrayList<SongInput> songs;
    private int followers = 0;
    private Integer duration;
    private Integer playedForTime = 0;
    private ArrayList<UserClass> followersList = new ArrayList<>();
    /**
     * Method that returns the number of seconds a playlist has been played
     */
    public int getFollowers() {
        return followers;
    }
    /**
     * Method that returns the number of seconds a playlist has been played
     */
    public Integer getDuration() {
        return duration;
    }
    /**
     * Method that sets the number of seconds a playlist has been played for
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
    }
    /**
     * Method that sets the number of folowers a playlist has
     */
    public void setFollowers(final int followers) {
        this.followers = followers;
    }
    /**
     * Method that adds a song to the playlist
     */
    public void addSong(final SongInput song) {
        songs.add(song);
    }
    /**
     * Method that removes a song from the playlist
     */
    public void removeSong(final SongInput song) {
        songs.remove(song);
    }
    /**
     * Method that returns the name of the playlist
     */
    public String getName() {
        return name;
    }
    /**
     * Method that sets the name of the playlist
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * Method that returns the state of the playlist (public or private)
     */
    public boolean isPublic() {
        return isPublic;
    }
    /**
     * Method that sets the state of the playlist (public or private)
     */
    public void setPublic(final boolean aPublic) {
        isPublic = aPublic;
    }
    /**
     * Method that returns the songs from a playlist
     */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }
    /**
     * Method that sets the songs from a playlist
     */
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }
    /**
     * Method that returns the owner of the playlist
     */
    public String getOwner() {
        return owner;
    }
    /**
     * Method that sets the owner of the playlist
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
    /**
     * Method that returns the folowers of the playlist
     */
    public ArrayList<UserClass> getFollowersList() {
        return followersList;
    }

}
