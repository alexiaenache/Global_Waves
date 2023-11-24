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
    private ArrayList<Playlist> playlists;
    private ObjectMapper mapper;
    private ArrayNode output;
    private ArrayList<String> likedSongs;
    private ArrayList<Integer> likes;


    public LibraryInput getLib() {
        return lib;
    }

    public void setLib(LibraryInput lib) {
        this.lib = lib;
    }
    public void setSerchedSongsUser(String user, ArrayList<SongInput> songs, String timestamp) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(user)) {
                this.users.get(i).setSearchedSongs(songs);
                this.users.get(i).removeSearchedPodcasts();
                this.users.get(i).setLastTimestamp(Integer.valueOf(timestamp));
                this.addOutputSearchMapper(this.users.get(i));
                users.get(i).setSearched(true);
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
                users.get(i).setSearched(true);
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
        } else if (user.getSearchedPodcasts() != null) {
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
        if (user.getSearchedPlaylists() != null) {
            no = user.getSearchedPlaylists().size();
        }
        node.put("message", "Search returned " + no + " results");
        ArrayList<String> playlistNames = new ArrayList<>();
        for (Playlist playlist : user.getSearchedPlaylists()) {
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
        if (user.isSearched()) {
            if (successfulSelect) {
                message = "Successfully selected " + user.getSelectedSearch() + ".";
            } else {
                message = "The selected ID is too high.";
                user.setSuccessfulSelect(false);
            }
        } else {
            message = "Please conduct a search before making a selection.";
            user.setSuccessfulSelect(false);
        }
        node.put("message", message);
        user.setSearched(false);
        this.output.add(node);
    }
    public void addOutputLoadMapper(UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "load");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message;
        if (user.getSearchedSongs() == null && user.getSearchedPodcasts() == null) {
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
        public void addOutputStatusMapper(UserClass user, String name, int remTime, String repM) {
            ObjectNode node = this.mapper.createObjectNode();
            node.put("command", "status");
            node.put("user", user.getUsername());
            node.put("timestamp", user.getLastTimestamp());
            if (remTime == 0) {
                name = "";
            }
            ObjectNode statsNode = this.mapper.createObjectNode();
            statsNode.put("name", name);
            statsNode.put("remainedTime", remTime);
            statsNode.put("repeat", repM);
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
        if (!user.isSuccessfullLoad()) {
            message = "Please load a source before attempting to pause or resume playback.";
        } else if (user.isSuccessfullLoad() && user.isPaused()) {
            message = "Playback paused successfully.";
        } else if (user.isSuccessfullLoad() && !user.isPaused()) {
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
        if (!found) {
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
        for (Playlist playlist : user.getPlaylists()) {
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
        if (user.getLikedSongs() != null) {
            for (SongInput song : user.getLikedSongs()) {
                results.add(song.getName());
            }
        }
        node.put("result", this.mapper.valueToTree(results));
        this.output.add(node);
    }
    public void addNewUser(UserInput user) {
        UserClass newUser = new UserClass();
        newUser.copyUser(user);
        this.users.add(newUser);
    }
    public void copyAllUsers(ArrayList<UserInput> users) {
        this.users = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
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

    public void addOutputFollow(UserClass user, String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "follow");
        node.put("message", message);
        node.put("timestamp", user.getLastTimestamp());
        node.put("user", user.getUsername());
        this.output.add(node);
    }
    public void addOutputSwitchVisibility(UserClass user, String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "switchVisibility");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }
    public void addOutputTop5PlaylistsMapper(Integer timestamp, ArrayList<Playlist> top5Playlists) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "getTop5Playlists");
        ArrayList<String> results = new ArrayList<>();

        for (Playlist playlist : top5Playlists) {
            results.add(playlist.getName());
        }
        node.put("result", this.mapper.valueToTree(results));
        node.put("timestamp", timestamp);
        this.output.add(node);
    }
    public void addOutputTop5SongsMapper(Integer integer, ArrayList<String> top5Songs) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "getTop5Songs");

        node.put("result", this.mapper.valueToTree(top5Songs));
        node.put("timestamp", integer);
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

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
    public void addInPlaylists(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public ArrayList<String> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(ArrayList<String> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public ArrayList<Integer> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Integer> likes) {
        this.likes = likes;
    }
    public void addLikedSong(String songName) {
        likedSongs.add(songName);
        likes.add(1); // Initialize with 1 like
    }

    public void removeLikedSong(String songName) {
        int index = likedSongs.indexOf(songName);
        if (index != -1) {
            likedSongs.remove(index);
            likes.remove(index);
        }
    }
    public void addLikeToSong(String songName) {
        int index = likedSongs.indexOf(songName);

        if (index == -1) {
            // Song not found, add it to the end with one like
            likedSongs.add(songName);
            likes.add(1);
        } else {
            // Song found, increase the number of likes by 1
            int currentLikes = likes.get(index);
            likes.set(index, currentLikes + 1);
        }
    }
    public void removeLikeFromSong(String songName) {
        int index = likedSongs.indexOf(songName);

        if (index != -1) {
            // Song found, decrease the number of likes by 1
            int currentLikes = likes.get(index);

            if (currentLikes > 0) {
                likes.set(index, currentLikes - 1);
            }

            // If the number of likes reaches 0, remove the song
            if (currentLikes == 1) {
                likedSongs.remove(index);
                likes.remove(index);
            }
        }
    }

}
