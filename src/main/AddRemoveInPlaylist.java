package main;

import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the methods for adding and removing songs from a playlist
 */
public class AddRemoveInPlaylist extends  Command {
    /**
     * Method that sets the command
     */
    public void setAddRemoveInPlaylist(final Command comm) {
        setCommand(comm.getCommand());
        setUsername(comm.getUsername());
        setTimestamp(comm.getTimestamp());
        setPlaylistId(comm.getPlaylistId());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        String message = "";
        boolean found = false;
        if (!user.isSuccessfullLoad()) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else if (user.getLoadedSong() == null) {
            message = "The loaded source is not a song.";
        } else if (user.getPlaylists() == null || user.getPlaylists().size() < getPlaylistId()) {
            message = "The specified playlist does not exist.";
        } else {
            ArrayList<Playlist> playlists = user.getPlaylists();
            for (int i = 1; i <= playlists.size(); i++) {
                if (i == getPlaylistId()) {
                    Playlist playlist = playlists.get(i - 1);

                    if (playlist.getSongs() == null) {
                        ArrayList<SongInput> songs = null;
                        songs = new ArrayList<>();
                        playlist.setSongs(songs);

                    }

                    if (playlist.getSongs() != null && playlist.getSongs().size() > 0) {
                        for (SongInput song : playlist.getSongs()) {
                            if (song.getName().equals(user.getLoadedSong().getName())) {
                                found = true;
                            }
                        }
                    }
                    if (found) {
                        playlist.removeSong(user.getLoadedSong());
                        message = "Successfully removed from playlist.";
                    } else {
                        playlist.addSong(user.getLoadedSong());
                        int a = playlist.getDuration();
                        int b = user.getLoadedSong().getDuration();
                        playlist.setDuration(a + b);
                        message = "Successfully added to playlist.";
                    }
                }
            }
        }
        player.addOutputAddRemoveInPlaylistMapper(user, message);
    }
}
