package main;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the methods for displaying the status of an user
 */
public class Status extends Command {
    /**
     * Method that sets the command
     */
    public void setLoad(final Command comm) {
        setCommand(comm.getCommand());
        setUsername(comm.getUsername());
        setTimestamp(comm.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        String repeatMessage = null;
        String name = null;
        int remainedTime = 0;
            if (user == null) {
                return;
            }
            user.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
            if (user.isSuccessfullLoad()) {
                if (user.getSearchedSongs() != null) {
                    ArrayList<SongInput> songs = user.getSearchedSongs();
                    SongInput select = null;
                    for (SongInput song : songs) {
                        if (song.getName().equals(user.getSelectedSearch())) {
                            select = song;
                            if (!user.isPaused()) {
                                int a = user.getRemainedTime();
                                int b = Integer.valueOf(this.getTimestamp()) - user.getLastPlay();
                                remainedTime = a - b;
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
                                timePassedPodcast += user.getPlayedPodcastForTime();
                            }
                            if (!user.isPaused()) {
                                int a = Integer.valueOf(this.getTimestamp());
                                int b = user.getLastPlay();
                                timePassed = a - b;
                            }
                            timePassedPodcast += timePassed;
                            for (EpisodeInput episode : podcast.getEpisodes()) {
                                if (aux < timePassed && timePassedPodcast
                                        <= aux + episode.getDuration()) {
                                    currenteEpisode = episode;
                                    name = episode.getName();
                                    break;
                                } else {
                                    aux += episode.getDuration();
                                }
                            }
                            if (currenteEpisode == null) {
                                return;
                            }
                            int a = currenteEpisode.getDuration();
                            int b = timePassedPodcast - aux;
                            remainedTime = a - b;
                            user.setPlayedPodcastForTime(timePassedPodcast);
                        }
                    }
                } else if (user.getSearchedPlaylists() != null) {
                    ArrayList<Playlist> playlists = user.getSearchedPlaylists();
                    for (Playlist playlist : playlists) {
                        if (playlist.getName().equals(user.getSelectedSearch())) {
                            if (!user.isPaused()) {
                                int a = user.getRemainedTime();
                                int b = Integer.valueOf(this.getTimestamp()) - user.getLastPlay();
                                remainedTime = a - b;
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



        if (remainedTime < 0 && user.getRepeat() == 0) {
            remainedTime = 0;
            user.setPaused(true);
        }
        player.addOutputStatusMapper(user, name, remainedTime, repeatMessage);
        }
    }

