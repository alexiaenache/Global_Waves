package main;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Status extends Command{
    public void setLoad(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
    }
    public void run (Player player) {
        int n = player.getUsers().size();
        UserClass user = null;
        String repeatMessage = null;
        String name = null;
        int remainedTime = 0;
        for(int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
        }
            if (user == null)
                return;
            user.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
            if (user.isSuccessfullLoad()) {
                if (user.getSearchedSongs() != null) {
                    ArrayList<SongInput> songs = user.getSearchedSongs();
                    SongInput select = null;
                    for (SongInput song : songs) {
                        if (song.getName().equals(user.getSelectedSearch())) {
                            select = song;
                            if (!user.isPaused()) {
                                remainedTime = user.getRemainedTime() - (Integer.valueOf(this.getTimestamp()) - user.getLastPlay());
                            } else {
                                remainedTime = user.getRemainedTime();
                            }
                            name = user.getSelectedSearch();
                        }
                    }
                } else if (user.getSearchedPodcasts() != null) {
                    ArrayList<PodcastInput> podcasts = user.getSearchedPodcasts();
                    PodcastInput select = null;
                    Integer timePassed = 0;
                    Integer aux = 0;
                    EpisodeInput currenteEpisode = null;
                    for (PodcastInput podcast : podcasts) {
                        if (podcast.getName().equals(user.getSelectedSearch())) {
                            Integer totalTimePodcast = 0;
                            for (EpisodeInput episode : podcast.getEpisodes()) {
                                totalTimePodcast += episode.getDuration();
                            }
                            Integer timePassedPodcast = totalTimePodcast - user.getRemainedTime();
                            if (user.getPlayedPodcastForTime() > 0) {
                                timePassedPodcast += user.getPlayedPodcastForTime() + 1;
                            }
                            if (!user.isPaused()) {
                                timePassed = Integer.valueOf(this.getTimestamp()) - user.getLastPlay();
                            }
                            timePassedPodcast += timePassed;
                            for (EpisodeInput episode : podcast.getEpisodes()) {
                                if (aux < timePassed && timePassedPodcast <= aux + episode.getDuration()) {
                                    currenteEpisode = episode;
//                                    remainedTime = episode.getDuration() - (timePassed - aux);
                                    name = episode.getName();
                                    break;
                                } else {
                                    aux += episode.getDuration();
                                }
                            }
                            if (currenteEpisode == null) {
                                return;
                            }
                            remainedTime = currenteEpisode.getDuration() - (timePassedPodcast - aux);
                            user.setPlayedPodcastForTime(timePassedPodcast);
                        }
                    }
                } else if (user.getSearchedPlaylists() != null) {
                    ArrayList<Playlist> playlists = user.getSearchedPlaylists();
                    Playlist select = null;
                    for (Playlist playlist : playlists) {
                        if (playlist.getName().equals(user.getSelectedSearch())) {
                            select = playlist;
                            if (!user.isPaused()) {
                                remainedTime = user.getRemainedTime() - (Integer.valueOf(this.getTimestamp()) - user.getLastPlay());
                            } else {
                                remainedTime = user.getRemainedTime();
                            }
                        }
                    }
                }
            }

            if (user.getRepeat() == 0) {
                repeatMessage = "No Repeat";
            } else if (user.getRepeat() == 1) {
                repeatMessage = "Repeat one";
            } else if (user.getRepeat() == 2) {
                repeatMessage = "Repeat Infinite";
            }



        if(remainedTime < 0 && user.getRepeat() == 0) {
            remainedTime = 0;
            user.setPaused(true);
        }
        player.addOutputStatusMapper(user, name, remainedTime, repeatMessage);
        }
    }

