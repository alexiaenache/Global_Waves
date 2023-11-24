package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Class that contains the methods for getting the top 5 songs
 */
public class GetTop5Songs extends Command {
    public static final int MAX = 5;
    /**
     * Method that sets the command
     */
    public void setGetTop5Songs(final Command c) {
        this.setCommand(c.getCommand());
        this.setTimestamp(c.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < player.getLikedSongs().size(); i++) {
            indices.add(i);
        }

        indices.sort(Comparator.comparingInt(i -> player.getLikes().get((Integer) i)).reversed());

        Collections.sort(player.getLikedSongs(), Comparator.comparingInt(song ->
                player.getLikes().get(player.getLikedSongs().indexOf(song))).reversed());
        Collections.sort(player.getLikes());
        ArrayList<String> top5Songs = null;
        if (player.getLikedSongs().size() > MAX) {
            top5Songs = new ArrayList<>(player.getLikedSongs().subList(0, MAX));
        } else {
            top5Songs = new ArrayList<>(player.getLikedSongs());
            if (player.getLikedSongs().size() < MAX) {
                for (int i = 0; i < MAX - player.getLikedSongs().size(); i++) {
                    top5Songs.add(player.getLib().getSongs().get(i).getName());
                }
            }
        }
        player.addOutputTop5SongsMapper(Integer.valueOf(getTimestamp()), top5Songs);

    }
}
