package main;
/**
 * Class that contains the methods for switching the visibility of a playlist
 */
public class SwitchVisibility extends Command {
    /**
     * Method that sets the command
     */
    public void setSwitchVisibility(final Command c) {
        setCommand(c.getCommand());
        setUsername(c.getUsername());
        setTimestamp(c.getTimestamp());
        setPlaylistId(c.getPlaylistId());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        boolean found = false;
        String message = "";
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        if (user.getPlaylists() == null || user.getPlaylists().size() < this.getPlaylistId()) {
            message = "The specified playlist ID is too high.";
        } else {
            Playlist playlist = user.getPlaylists().get(this.getPlaylistId() - 1);
            if (playlist.isPublic()) {
                playlist.setPublic(false);
                message = "Visibility status updated successfully to private.";
            } else {
                playlist.setPublic(true);
                message = "Visibility status updated successfully to public.";
            }
        }
        player.addOutputSwitchVisibility(user, message);
    }



}
