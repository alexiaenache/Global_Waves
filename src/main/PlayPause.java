package main;
/**
 * Class that contains the methods for play/pause
 */
public class PlayPause extends Command {
    /**
     * Method that sets the command
     */
    public void setPlayPause(final Command c) {
        this.setCommand(c.getCommand());
        this.setUsername(c.getUsername());
        this.setTimestamp(c.getTimestamp());
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
        if (user == null) {
            return;
        }
        String message = "";
        user.setLastTimestamp(Integer.valueOf(getTimestamp()));
        if (user.isPaused()) {
            user.setPaused(false);
            user.setLastPlay(Integer.valueOf(getTimestamp()));
        } else {
            user.setPaused(true);
            int a = user.getRemainedTime();
            int b = Integer.valueOf(getTimestamp()) - user.getLastPlay();
            user.setRemainedTime(a - b);
        }
        player.addOutputPlayPauseMapper(user);
    }

}
