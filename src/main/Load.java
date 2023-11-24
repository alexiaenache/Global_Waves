package main;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the methods for loading a source
 */
public class Load extends Command {
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
        UserClass user = null;
        user = player.whichUser(this.getUsername());
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
            if (s != null) {
                user.setRemainedTime(s.getDuration());
            }
        } else if (user.getSearchedPodcasts() != null && !user.getSearchedPodcasts().isEmpty()) {
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
            if (user.getLoadedPodcast() == null) {
                return;
            }
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
            if (user.getLoadedPlaylist() == null) {
                return;
            }
            user.setRemainedTime(user.getLoadedPlaylist().getDuration());
        }
        player.addOutputLoadMapper(user);
    }
}

