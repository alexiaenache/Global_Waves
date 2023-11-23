package main;

public class Select extends Command {
    public void setSelect(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setItemNumber(comm.getItemNumber());
    }

    public void run(Player player) {
        UserClass u = new UserClass();
        for (int i = 0; i < player.getUsers().size(); i++) {
            if (player.getUsers().get(i).getUsername().equals(this.getUsername())) {
                u = player.getUsers().get(i);
                u.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
                if (player.getUsers().get(i).getSearchedSongs() != null && player.getUsers().get(i).getSearchedSongs().size() != 0) {
                    if ((this.getItemNumber() - 1) >= player.getUsers().get(i).getSearchedSongs().size()) {
//                        System.out.println("Selectia nu a fost gasita");
                        u.setSuccessfulSelect(false);
//                        u.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
                    } else {
//                        System.out.println("Selectia este: " + player.getUsers().get(i).getSearchedSongs().get(this.getItemNumber() - 1).getName());
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(player.getUsers().get(i).getSearchedSongs().get(this.getItemNumber() - 1).getName(), this.getTimestamp());
//                        player.addOutputSelectMapper(player.getUsers().get(i));
                    }
                } else if (player.getUsers().get(i).getSearchedPodcasts() != null && player.getUsers().get(i).getSearchedPodcasts().size() != 0) {
                    if ((this.getItemNumber() - 1 )>= player.getUsers().get(i).getSearchedPodcasts().size()) {
//                        System.out.println("Selectia nu a fost gasita");
                        u.setSuccessfulSelect(false);
//                        u.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
                    } else {
//                        System.out.println("Selectia este: " + player.getUsers().get(i).getSearchedPodcasts().get(this.getItemNumber() - 1).getName());
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(player.getUsers().get(i).getSearchedPodcasts().get(this.getItemNumber() - 1).getName(), this.getTimestamp());

                    }
                } else if(player.getUsers().get(i).getSearchedPlaylists() != null && player.getUsers().get(i).getSearchedPlaylists().size() != 0) {
//                    System.out.println(player.getUsers().get(i).getSearchedPlaylists().size());
                    if ((this.getItemNumber() - 1) >= player.getUsers().get(i).getSearchedPlaylists().size()) {
                        u.setSuccessfulSelect(false);
                    } else {
                        u.setSuccessfulSelect(true);
                        u.selectedSearch(player.getUsers().get(i).getSearchedPlaylists().get(this.getItemNumber() - 1).getName(), this.getTimestamp());
//                        System.out.println(player.getUsers().get(i).getSearchedPlaylists().get(this.getItemNumber() - 1).getName());
                    }
                }
            }
        }

       player.addOutputSelectMapper(u, u.isSuccessfulSelect());

    }


}

