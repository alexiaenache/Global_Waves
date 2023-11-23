package main;

public class ShowPreferredSongs extends Command{
    public void setShowPreferredSongs(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
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
        player.addOutputShowPreferredSongsMapper(user);
    }
}
