package main;

import java.util.ArrayList;
/**
 * Class that contains the methods for creating a playlist
 */
public class CreatePlaylist extends Command {
    /**
     * Method that sets the command
     */
    public void setCreatePlaylist(final Command comm) {
        setCommand(comm.getCommand());
        setUsername(comm.getUsername());
        setTimestamp(comm.getTimestamp());
        setPlaylistName(comm.getPlaylistName());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        boolean found = false;
        if (user.getPlaylists() != null) {
            ArrayList<Playlist> playlists = user.getPlaylists();
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(this.getPlaylistName())) {
                    found = true;
                }
            }

        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        Playlist playlist = null;
        if (!found) {
            playlist = new Playlist();
            playlist.setName(this.getPlaylistName());
            playlist.setPublic(true);
            playlist.setOwner(user.getUsername());
            playlist.setFollowers(0);
            playlist.setDuration(0);
            player.addInPlaylists(playlist);
            if (user.getPlaylists() == null) {
                user.setPlaylists(new ArrayList<>());
            }
            user.getPlaylists().add(playlist);
        }
        player.addOutputCreatePlaylist(user, found);
    }

}
