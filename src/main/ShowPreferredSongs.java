package main;
/**
 * Class that contains the methods for displaying the songs that a user liked
 */
public class ShowPreferredSongs extends Command {
    /**
     * Method that sets the command
     */
    public void setShowPreferredSongs(final Command comm) {
        setCommand(comm.getCommand());
        setUsername(comm.getUsername());
        setTimestamp(comm.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        player.addOutputShowPreferredSongsMapper(user);
    }
}
