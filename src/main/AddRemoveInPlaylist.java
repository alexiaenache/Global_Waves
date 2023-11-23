package main;

import fileio.input.SongInput;

import java.util.ArrayList;

public class AddRemoveInPlaylist extends  Command{
    public void setAddRemoveInPlaylist(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setPlaylistId(comm.getPlaylistId());
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
        String message = "";
        boolean found = false;
        if(!user.isSuccessfullLoad()) {
            message = "Please load a source before adding to or removing from the playlist.";
        } else if(user.getLoadedSong() == null) {
            message = "The loaded source is not a song.";
        } else if(user.getPlaylists() == null || user.getPlaylists().size() < getPlaylistId()) {
            message = "The specified playlist does not exist.";
        } else {
            ArrayList<Playlist> playlists = user.getPlaylists();
            for (int i = 1; i <= playlists.size(); i++) {
                if (i == getPlaylistId()) {
                    Playlist playlist = playlists.get(i - 1);

                    if(playlist.getSongs() == null) {
                        ArrayList<SongInput> songs = null;
                        songs = new ArrayList<>();
                        playlist.setSongs(songs);

                    }

                    if(playlist.getSongs() != null && playlist.getSongs().size() > 0) {
                        for (SongInput song : playlist.getSongs()) {
                            if (song.getName().equals(user.getLoadedSong().getName())) {
                                found = true;
                            }
                        }
                    }
                    if(found) {
                        playlist.removeSong(user.getLoadedSong());
                        message = "Successfully removed from playlist.";
                    } else {
                        playlist.addSong(user.getLoadedSong());
                        playlist.setDuration(playlist.getDuration() + user.getLoadedSong().getDuration());
                        message = "Successfully added to playlist.";
                    }
                }
            }
        }
        player.addOutputAddRemoveInPlaylistMapper(user, message);
    }
}
