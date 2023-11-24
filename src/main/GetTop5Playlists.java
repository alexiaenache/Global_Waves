package main;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class that contains the methods for getting the top 5 playlists
 */
public class GetTop5Playlists extends Command {
    public static final int MAX = 5;
    /**
     * Method that sets the command
     */
    public void setGetTop5Playlists(final Command c) {
        setCommand(c.getCommand());
        setTimestamp(c.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        ArrayList<Playlist> playlists = player.getPlaylists();

        playlists.sort(Comparator.comparingInt(playlist ->
                ((Playlist) playlist).getFollowersList().size()).reversed());

        ArrayList<Playlist> top5Playlists = null;
        if (playlists.size() > MAX) {
            top5Playlists = new ArrayList<>(playlists.subList(0, MAX));
        } else {
            top5Playlists = new ArrayList<>(playlists);
        }
        player.addOutputTop5PlaylistsMapper(Integer.valueOf(getTimestamp()), top5Playlists);
    }
}
