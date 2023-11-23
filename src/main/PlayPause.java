package main;

public class PlayPause extends Command {
    public void setPlayPause(Command c) {
        this.setCommand(c.getCommand());
        this.setUsername(c.getUsername());
        this.setTimestamp(c.getTimestamp());
    }
    public void run(Player player) {
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
