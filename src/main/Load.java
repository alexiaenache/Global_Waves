package main;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
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
        for (int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }

        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        if (user.getSearchedSongs() != null && !user.getSearchedSongs().isEmpty()) {

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
        }
        else if (user.getSearchedPodcasts() != null && !user.getSearchedPodcasts().isEmpty()) {
            ArrayList<PodcastInput> podcasts = user.getSearchedPodcasts();
            PodcastInput p = null;
            for (PodcastInput podcast : podcasts) {
                if (podcast.getName().equals(user.getSelectedSearch())) {
                    p = podcast;
                }
            }
            if (p != null) {
                user.setLoadedPodcast(p);
            }
            user.setLastPlay(Integer.valueOf(getTimestamp()));
            user.setPaused(false);
            if (user.getLoadedPodcast() == null)
                return;
            ArrayList<EpisodeInput> episodes = user.getLoadedPodcast().getEpisodes();
            int d = 0;
                for (EpisodeInput episode : episodes) {
                    d += episode.getDuration();
                }
                if (p != null) {
                    user.setLoadedPodcast(p);
                }

            user.setRemainedTime(d);
        } else if (user.getSearchedPlaylists() != null && !user.getSearchedPlaylists().isEmpty()) {
            ArrayList<Playlist> playlists = user.getSearchedPlaylists();
            Playlist p = null;
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(user.getSelectedSearch())) {
                    p = playlist;
                }
            }
            if (p != null) {
                user.setLoadedPlaylist(p);
            }
            user.setLastPlay(Integer.valueOf(getTimestamp()));
            user.setPaused(false);
            if (user.getLoadedPlaylist() == null)
                return;
            user.setRemainedTime(user.getLoadedPlaylist().getDuration());
        }
        player.addOutputLoadMapper(user);
    }
}

