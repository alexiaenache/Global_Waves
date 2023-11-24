package main;

import java.util.ArrayList;
/**
 * Class that contains the information about a filters, used for parsing
 */
public class Filters {
    private String name;
    private String album;
    private String owner;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private ArrayList<String> tags;
    /**
     * Method that returns the album
     */
    public String getAlbum() {
        return album;
    }
    /**
     * Method that sets the album
     */
    public void setAlbum(final String album) {
        this.album = album;
    }
    /**
     * Method that return the lyrics
     */
    public String getLyrics() {
        return lyrics;
    }
    /**
     * Method that sets the lyrics
     */

    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }
    /**
     * Method that returns the genre
     */

    public String getGenre() {
        return genre;
    }
    /**
     * Method that sets the genre
     */

    public void setGenre(final String genre) {
        this.genre = genre;
    }
    /**
     * Method that returns the release year
     */

    public String getReleaseYear() {
        return releaseYear;
    }
    /**
     * Method that sets the release year
     */

    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }
    /**
     * Method that returns the artist
     */

    public String getArtist() {
        return artist;
    }
    /**
     * Method that sets the artist
     */

    public void setArtist(final String artist) {
        this.artist = artist;
    }
    /**
     * Method that returns the owner
     */

    public String getOwner() {
        return owner;
    }
    /**
     * Method that sets the owner
     */

    public void setOwner(final String owner) {
        this.owner = owner;
    }
    /**
     * Method that returns the name
     */
    public String getName() {
        return name;
    }
    /**
     * Method that sets the name
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * Method that returns the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }
    /**
     * Method that sets the tags
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }
}
