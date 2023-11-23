package main;

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
        int remainedTime = 0;
        for(int i = 0; i < n; i++) {
            if(this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
            if(user == null)
                return;
            user.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
            if(user.isSuccessfullLoad()) {
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
                        }
                    }
                } else if(user.getSearchedPodcasts() != null) {
                    ArrayList<PodcastInput> podcasts = user.getSearchedPodcasts();
                    PodcastInput select = null;
                    for (PodcastInput podcast : podcasts) {
                        if (podcast.getName().equals(user.getSelectedSearch())) {
                            select = podcast;
                            if (!user.isPaused()) {
                                remainedTime = user.getRemainedTime() - (Integer.valueOf(this.getTimestamp()) - user.getLastPlay());
                            } else {
                                remainedTime = user.getRemainedTime();
                            }
                        }
                    }
                }
            }
            if(user.getRepeat() == 0) {
                repeatMessage = "No Repeat";
            } else if(user.getRepeat() == 1) {
                repeatMessage = "Repeat one";
            } else if(user.getRepeat() == 2) {
                repeatMessage = "Repeat Infinite";
            }

            }
        if(remainedTime < 0 && user.getRepeat() == 0) {
            remainedTime = 0;
            user.setPaused(true);
        }
        player.addOutputStatusMapper(user, user.getSelectedSearch(), remainedTime, repeatMessage);
        }
    }

