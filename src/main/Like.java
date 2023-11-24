package main;

import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the methods for liking and unliking a song
 */
public class Like extends Command {
    /**
     * Method that sets the command
     */
    public void setLike(final Command c) {
        setCommand(c.getCommand());
        setUsername(c.getUsername());
        setTimestamp(c.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        if (player.getLikedSongs() == null) {
            ArrayList<String> likedSongs = new ArrayList<>();
            player.setLikedSongs(likedSongs);
        }
        if (player.getLikes() == null) {
            ArrayList<Integer> likes = new ArrayList<>();
            player.setLikes(likes);
        }
        UserClass user = player.whichUser(getUsername());
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
                } else {
                    user.addLikedSong(user.getLoadedSong());
                    player.addLikeToSong(user.getLoadedSong().getName());
                    message = "Like registered successfully.";
                }

        }
        player.addOutputLikeMapper(user, message);
    }
}
