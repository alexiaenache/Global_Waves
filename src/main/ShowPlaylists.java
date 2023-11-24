package main;
/**
 * Class that contains the methods for displaying a playlists
 */
public class ShowPlaylists extends Command {
    /**
     * Method that sets the command
     */
    public void setShowPlaylists(final Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        int n = player.getUsers().size();
        UserClass user = null;
        for (int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
        }
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        player.addOutputShowPlaylistsMapper(user);
    }
}
