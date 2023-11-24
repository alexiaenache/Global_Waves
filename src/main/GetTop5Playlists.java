package main;

import java.util.ArrayList;
import java.util.Comparator;

public class GetTop5Playlists extends Command {
    public void setGetTop5Playlists(Command c) {
        this.setCommand(c.getCommand());
        this.setTimestamp(c.getTimestamp());
    }
    public void run(Player player) {
        ArrayList <Playlist> playlists = player.getPlaylists();

        playlists.sort(Comparator.comparingInt(playlist ->
                ((Playlist) playlist).getFollowersList().size()).reversed());

        ArrayList<Playlist> top5Playlists = null;
        if(playlists.size() > 5) {
            top5Playlists = new ArrayList<>(playlists.subList(0, 5));
        } else {
            top5Playlists = new ArrayList<>(playlists);
        }
        player.addOutputTop5PlaylistsMapper(Integer.valueOf(getTimestamp()), top5Playlists);
    }
}
