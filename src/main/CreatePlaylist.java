package main;

import java.util.ArrayList;

public class CreatePlaylist extends Command{
    public void setCreatePlaylist(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setPlaylistName(comm.getPlaylistName());
    }
    public void run(Player player) {
        int n = player.getUsers().size();
        UserClass user = null;

        for(int i = 0; i < n; i++) {
            if(this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
        }
        boolean found = false;
        if(user.getPlaylists() != null) {
            ArrayList<Playlist> playlists = user.getPlaylists();
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(this.getPlaylistName())) {
                    found = true;
                }
            }

        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        Playlist playlist = null;
        if(!found) {
            playlist = new Playlist();
            playlist.setName(this.getPlaylistName());
            playlist.setPublic(true);
            playlist.setOwner(user.getUsername());
            playlist.setFollowers(0);
            playlist.setDuration(0);
            if (user.getPlaylists() == null) {
                user.setPlaylists(new ArrayList<>());
            }
            user.getPlaylists().add(playlist);
        }
        player.addOutputCreatePlaylist(user, found);
    }

}
