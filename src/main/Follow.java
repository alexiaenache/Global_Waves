package main;
/**
 * Class that contains the methods for following a playlist
 */
public class Follow extends Command {
    /**
     * Method that sets the command
     */
    public void setFollow(final Command c) {
        setCommand(c.getCommand());
        setUsername(c.getUsername());
        setTimestamp(c.getTimestamp());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass user = player.whichUser(getUsername());
        if (user == null) {
            return;
        }
        String message = "";
        boolean found = false;
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        if (user.getSelectedSearch() == null) {
            message = "Please select a source before following or unfollowing.";
            player.addOutputFollow(user, message);
            return;
        }
        if (player.getPlaylists() != null && !player.getPlaylists().isEmpty()) {
            for (Playlist playlist : player.getPlaylists()) {
                if (playlist.getName().equals(user.getSelectedSearch())) {
                    found = true;
                    if (playlist.getOwner().contains(user.getUsername())) {
                        message = "You cannot follow or unfollow your own playlist.";
                    } else {
                        if (playlist.getFollowersList().contains(user)) {
                            playlist.getFollowersList().remove(user);
                            playlist.setFollowers(playlist.getFollowers() - 1);
                            message = "Playlist unfollowed successfully.";
                        } else {
                            playlist.getFollowersList().add(user);
                            playlist.setFollowers(playlist.getFollowers() + 1);
                            message = "Playlist followed successfully.";
                        }
                    }
                    break;
                }
            }
        }
        if (!found) {
            message = "The selected source is not a playlist.";
        }
        player.addOutputFollow(user, message);
    }
}
