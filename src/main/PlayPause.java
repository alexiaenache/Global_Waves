package main;
/**
 * Class that contains the methods for play/pause
 */
public class PlayPause extends Command {
    /**
     * Method that sets the command
     */
    public void setPlayPause(final Command c) {
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
