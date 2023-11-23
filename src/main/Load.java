package main;

import fileio.input.SongInput;

import java.util.ArrayList;

public class Load extends Command {
    public void setLoad(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
    }
    public void run(Player player) {
        int n = player.getUsers().size();
        UserClass user = null;
        for(int i = 0; i < n; i++) {
            if(this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }

        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
//        if(user.getSelectedSearch() == null) {
//        }
        if(user.getSearchedSongs() != null && !user.getSearchedSongs().isEmpty()) {

            ArrayList<SongInput> songs = user.getSearchedSongs();
            SongInput s = null;
            for (SongInput song : songs) {
                if (song.getName().equals(user.getSelectedSearch())) {
                    s = song;
                }
            }
            user.setLoadedSong(s);
            user.setLastPlay(Integer.valueOf(getTimestamp()));
            user.setPaused(false);
            if (s == null) {
                System.out.println(user.getLastTimestamp());
            } else {
                user.setRemainedTime(s.getDuration());
            }
        } else if(user.getSearchedPodcasts() != null && !user.getSearchedPodcasts().isEmpty()) {
            user.setLastPlay(Integer.valueOf(getTimestamp()));
            user.setPaused(false);
            user.setRemainedTime(user.getLoadedPodcast().getDuration());
        } else if(user.getSearchedPlaylists() != null && !user.getSearchedPlaylists().isEmpty()) {
            user.setLastPlay(Integer.valueOf(getTimestamp()));
            user.setPaused(false);
            user.setRemainedTime(user.getLoadedPlaylist().getDuration());
        }
        player.addOutputLoadMapper(user);
    }
}

