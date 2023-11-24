package main;

public class SwitchVisibility extends Command {
    public void setSwitchVisibility(Command c) {
        this.setCommand(c.getCommand());
        this.setUsername(c.getUsername());
        this.setTimestamp(c.getTimestamp());
        this.setPlaylistId(c.getPlaylistId());
    }
    public void run(Player player) {
        int n = player.getUsers().size();
        UserClass user = null;

        for (int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }
        }
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
