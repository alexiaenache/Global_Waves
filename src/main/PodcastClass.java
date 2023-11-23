package main;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

public class PodcastClass {
    private String name;
    private String owner;
    private ArrayList<EpisodeInput> episodes;
    private Integer playedForTime = 0;

    public Integer getPlayedForTime() {
        return playedForTime;
    }

    public void setPlayedForTime(Integer playedForTime) {
        this.playedForTime = playedForTime;
    }

    public PodcastClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }
}
