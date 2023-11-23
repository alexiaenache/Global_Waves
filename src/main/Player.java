package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;

public class Player {
    private LibraryInput lib;
    private ArrayList<UserClass> users;
    private ObjectMapper mapper;
    private ArrayNode output;



    public LibraryInput getLib() {
        return lib;
    }

    public void setLib(LibraryInput lib) {
        this.lib = lib;
    }
    public void setSerchedSongsUser(String user, ArrayList<SongInput> songs, String timestamp) {
        for(int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(user)) {
                this.users.get(i).setSearchedSongs(songs);
                this.users.get(i).removeSearchedPodcasts();
                this.users.get(i).setLastTimestamp(Integer.valueOf(timestamp));
                this.addOutputSearchMapper(this.users.get(i));
            }
        }
    }
    public void setSearchedPodcastsUser(String user, ArrayList<PodcastInput> podcasts, String timestamp) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(user)) {
                this.users.get(i).setSearchedPodcasts(podcasts);
                this.users.get(i).removeSearchedSongs();
                this.users.get(i).setLastTimestamp(Integer.valueOf(timestamp));
                this.addOutputSearchMapper(this.users.get(i));
            }
        }
    }
    public void addOutputSearchMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "search");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        if (user.getSearchedSongs() != null) {
            node.put("message", "Search returned " + user.getSearchedSongs().size() + " results");
            ArrayList<String> songNames = new ArrayList<>();
            for (SongInput song : user.getSearchedSongs()) {
                songNames.add(song.getName());
            }
            node.put("results", this.mapper.valueToTree(songNames));
        } else if(user.getSearchedPodcasts() != null) {
            node.put("message", "Search returned " + user.getSearchedPodcasts().size() + " results");
            ArrayList<String> podcastNames = new ArrayList<>();
            for (PodcastInput podcast : user.getSearchedPodcasts()) {
                podcastNames.add(podcast.getName());
            }
            node.put("results", this.mapper.valueToTree(podcastNames));
        }
        this.output.add(node);
    }
    public void addOutputSearchPlaylistMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "search");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        int no = 0;
        if(user.getPlaylists() != null) {
            no = user.getPlaylists().size();
        }
        node.put("message", "Search returned " + no + " results");
        ArrayList<String> playlistNames = new ArrayList<>();
        for(Playlist playlist : user.getSearchedPlaylists()) {
            playlistNames.add(playlist.getName());
        }
//        System.out.println(playlistNames.size());
//        System.out.println(playlistNames.get(0));
        node.put("results", this.mapper.valueToTree(playlistNames));
        this.output.add(node);
    }
    public void addOutputSelectMapper(UserClass user, boolean successfulSelect) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "select");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message;
        boolean isSongs, isPodcasts, isPlaylists;
        if(user.getSearchedSongs() == null || user.getSearchedSongs().isEmpty()) {
            isSongs = false;
        } else {
            isSongs = true;
        }
        if(user.getSearchedPodcasts() == null || user.getSearchedPodcasts().isEmpty()) {
            isPodcasts = false;
        } else {
            isPodcasts = true;
        }
        if(user.getSearchedPlaylists() == null || user.getSearchedPlaylists().isEmpty()) {
            isPlaylists = false;
        } else {
            isPlaylists = true;
        }
        if(isSongs || isPodcasts || isPlaylists) {
            if (successfulSelect) {
                message = "Successfully selected " + user.getSelectedSearch() + ".";
            } else {
                message = "The selected ID is too high.";
            }
        } else {
            message = "Please conduct a search before making a selection.";
        }
        node.put("message", message);
        this.output.add(node);
    }
    public void addOutputLoadMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "load");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message;
        if(user.getSearchedSongs() == null && user.getSearchedPodcasts()==null) {
            message = "You can't load an empty audio collection!";
        } else if(!user.isSuccessfulSelect()) {
            message = "Please select a source before attempting to load.";
        } else {
            message = "Playback loaded successfully.";
            user.setSuccessfullLoad(true);
        }
        node.put("message", message);
        this.output.add(node);
        }
        public void addOutputStatusMapper(UserClass user, String selectedSearch, int remainedTime, String repeatMessage) {
            ObjectNode node = this.mapper.createObjectNode();
            node.put("command", "status");
            node.put("user", user.getUsername());
            node.put("timestamp", user.getLastTimestamp());
            if(remainedTime == 0)
                selectedSearch = "";
            ObjectNode statsNode = this.mapper.createObjectNode();
            statsNode.put("name", selectedSearch);
            statsNode.put("remainedTime", remainedTime);
            statsNode.put("repeat", repeatMessage);
            statsNode.put("shuffle", user.isShuffle());
            statsNode.put("paused", user.isPaused());
            node.set("stats", statsNode);
            this.output.add(node);
    }
    public void addOutputPlayPauseMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "playPause");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message = null;
        if(!user.isSuccessfullLoad()) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if(user.isSuccessfullLoad() && user.isPaused()) {
            message = "Playback paused successfully.";
        } else if(user.isSuccessfullLoad() && !user.isPaused()){
            message = "Playback resumed successfully.";
        }
        node.put("message", message);
        this.output.add(node);
    }
    public void addOutputCreatePlaylist(UserClass user, boolean found) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "createPlaylist");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message = null;
        if(!found) {
            message = "Playlist created successfully.";
        } else {
            message = "A playlist with the same name already exists.";
        }
        node.put("message", message);
        this.output.add(node);
    }
    public void addOutputAddRemoveInPlaylistMapper(UserClass user, String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "addRemoveInPlaylist");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }
    public void addOutputShowPlaylistsMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "showPlaylists");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        ArrayList<ObjectNode> results = new ArrayList<>();
        for(Playlist playlist : user.getPlaylists()) {
            ObjectNode playlistNode = this.mapper.createObjectNode();
            playlistNode.put("name", playlist.getName());
            String type = "";
            ArrayList<String> songNames = new ArrayList<>();
            for (SongInput song : playlist.getSongs()) {
                songNames.add(song.getName());
            }
            playlistNode.put("songs", this.mapper.valueToTree(songNames));
            if (playlist.isPublic()) {
                type = "public";
            } else {
                type = "private";
            }
            playlistNode.put("visibility", type);
            playlistNode.put("followers", playlist.getFollowers());
            results.add(playlistNode);
        }

        node.set("result", this.mapper.valueToTree(results));
        this.output.add(node);
    }
    public void addOutputShowPreferredSongsMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "showPreferredSongs");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        ArrayList<String> results = new ArrayList<>();
        if(user.getLikedSongs() != null) {
            for (SongInput song : user.getLikedSongs()) {
                results.add(song.getName());
            }
            node.put("result", this.mapper.valueToTree(results));
        }
        this.output.add(node);
    }
    public void addNewUser(UserInput user) {
        UserClass newUser = new UserClass();
        newUser.copyUser(user);
        this.users.add(newUser);
    }
    public void copyAllUsers(ArrayList<UserInput> users) {
        this.users = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            this.users.add(new UserClass());
            this.users.get(i).setUsername(users.get(i).getUsername());
            this.users.get(i).setAge(users.get(i).getAge());
            this.users.get(i).setCity(users.get(i).getCity());
        }
    }
    public void addOutputLikeMapper(UserClass user, String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "like");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }

    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(ArrayNode output) {
        this.output = output;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public ArrayList<UserClass> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserClass> users) {
        this.users = users;
    }


}
