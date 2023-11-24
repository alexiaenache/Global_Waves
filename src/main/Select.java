package main;
/**
 * Class that contains the methods for select
 */
public class Select extends Command {
    /**
     * Method that sets the command
     */
    public void setSelect(final Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setItemNumber(comm.getItemNumber());
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        UserClass u = new UserClass();
        for (int i = 0; i < player.getUsers().size(); i++) {
            if (player.getUsers().get(i).getUsername().equals(this.getUsername())) {
                u = player.getUsers().get(i);
                u.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
                if (u.getSearchedSongs() != null && u.getSearchedSongs().size() != 0) {
                    if ((this.getItemNumber() - 1)
                            >= player.getUsers().get(i).getSearchedSongs().size()) {
                        u.setSuccessfulSelect(false);
                    } else {
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(u.getSearchedSongs()
                                .get(this.getItemNumber() - 1).getName(), this.getTimestamp());
                    }
                } else if (u.getSearchedPodcasts()
                        != null && u.getSearchedPodcasts().size() != 0) {
                    if ((this.getItemNumber() - 1) >= u.getSearchedPodcasts().size()) {
                        u.setSuccessfulSelect(false);
                    } else {
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(u.getSearchedPodcasts()
                                .get(this.getItemNumber() - 1).getName(), this.getTimestamp());

                    }
                } else if (u.getSearchedPlaylists() != null
                        && u.getSearchedPlaylists().size() != 0) {
                    if ((this.getItemNumber() - 1) >= u.getSearchedPlaylists().size()) {
                        u.setSuccessfulSelect(false);
                    } else {
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(u.getSearchedPlaylists()
                                .get(this.getItemNumber() - 1).getName(), this.getTimestamp());
                    }
                }
            }
        }
       player.addOutputSelectMapper(u, u.isSuccessfulSelect());

    }


}

