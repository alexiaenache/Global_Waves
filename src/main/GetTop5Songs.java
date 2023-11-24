package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetTop5Songs extends Command {
    public void setGetTop5Songs(Command c) {
        this.setCommand(c.getCommand());
        this.setTimestamp(c.getTimestamp());
    }
    public void run(Player player) {
        System.out.println(player.getLikedSongs().size());
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < player.getLikedSongs().size(); i++) {
            indices.add(i);
        }

        indices.sort(Comparator.comparingInt(i -> player.getLikes().get((Integer) i)).reversed());

        Collections.sort(player.getLikedSongs(), Comparator.comparingInt(song -> player.getLikes().get(player.getLikedSongs().indexOf(song))).reversed());
        Collections.sort(player.getLikes());
        ArrayList<String> top5Songs = null;
        if(player.getLikedSongs().size() > 5) {
            top5Songs = new ArrayList<>(player.getLikedSongs().subList(0, 5));
        } else {
            top5Songs = new ArrayList<>(player.getLikedSongs());
            if(player.getLikedSongs().size() < 5) {
                for (int i = 0; i < 5 - player.getLikedSongs().size(); i++) {
                    top5Songs.add(player.getLib().getSongs().get(i).getName());
                }
            }
        }
        player.addOutputTop5SongsMapper(Integer.valueOf(getTimestamp()), top5Songs);

    }
}
