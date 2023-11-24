package main;

import fileio.input.SongInput;

import java.util.ArrayList;

public class Like extends Command {
    public void setLike(Command c) {
        this.setCommand(c.getCommand());
        this.setUsername(c.getUsername());
        this.setTimestamp(c.getTimestamp());
    }
    public void run(Player player) {
        if(player.getLikedSongs() == null) {
            ArrayList<String> likedSongs = new ArrayList<>();
            player.setLikedSongs(likedSongs);
        }
        if(player.getLikes() == null) {
            ArrayList<Integer> likes = new ArrayList<>();
            player.setLikes(likes);
        }
        int n = player.getUsers().size();
        UserClass user = null;
        for (int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        String message = "";
        boolean found = false;
        if (!user.isSuccessfullLoad()) {
            message = "Please load a source before liking or unliking.";
        } else if (user.getLoadedSong() == null) {
            message = "Loaded source is not a song.";
        } else {
            if (user.getLikedSongs() == null) {
                ArrayList<SongInput> songs = new ArrayList<>();
                user.setLikedSongs(songs);
            } else {
                for (SongInput song : user.getLikedSongs()) {
                    if (song.getName().equals(user.getLoadedSong().getName())) {
                        found = true;
                    }
                }
            }
                if (found) {
                    user.removeLikedSong(user.getLoadedSong());
                    player.removeLikeFromSong(user.getLoadedSong().getName());
                    message = "Unlike registered successfully.";
                    System.out.println("unliked" + user.getLoadedSong().getName());
                } else {
                    user.addLikedSong(user.getLoadedSong());
                    player.addLikeToSong(user.getLoadedSong().getName());
                    message = "Like registered successfully.";
                    System.out.println("liked" + user.getLoadedSong().getName());
                }

        }
        player.addOutputLikeMapper(user, message);
    }
}
