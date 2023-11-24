package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;
/**
 * Class that represents the application, contains the methods for the commands
 */
public class Player {
    private LibraryInput lib;
    private ArrayList<UserClass> users;
    private ArrayList<Playlist> playlists;
    private ObjectMapper mapper;
    private ArrayNode output;
    private ArrayList<String> likedSongs;
    private ArrayList<Integer> likes;

    /**
     * Method that returns the library
     */
    public LibraryInput getLib() {
        return lib;
    }
    /**
     * Method that sets the library
     */
    public void setLib(final LibraryInput lib) {
        this.lib = lib;
    }
    /**
     * Method that sets the serached songs for a user
     */
    public void setSerchedSongsUser(
            final String user, final ArrayList<SongInput> songs, final String timestamp) {
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
    /**
     * Method that sets the searched podcasts for a user
     */
    public void setSearchedPodcastsUser(
            final String user, final ArrayList<PodcastInput> podcasts, final String timestamp) {
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
    /**
     * Method that adds the searched songs for a user
     */
    public void
    addOutputSearchMapper(final UserClass user) {
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
            node.put("message", "Search returned "
                    + user.getSearchedPodcasts().size() + " results");
            ArrayList<String> podcastNames = new ArrayList<>();
            for (PodcastInput podcast : user.getSearchedPodcasts()) {
                podcastNames.add(podcast.getName());
            }
            node.put("results", this.mapper.valueToTree(podcastNames));
        }
        this.output.add(node);
    }
    /**
     * Method that that adds the output for the searched playlists
     */
    public void addOutputSearchPlaylistMapper(final UserClass user) {
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
        node.put("results", this.mapper.valueToTree(playlistNames));
        this.output.add(node);
    }
    /**
     * Method that adds the output for the select command
     */
    public void
    addOutputSelectMapper(final UserClass user, final boolean successfulSelect) {
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
    /**
     * Method that adds the output for the load command
     */
    public void addOutputLoadMapper(final UserClass user) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "load");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        String message;
        if (user.getSearchedSongs() == null && user.getSearchedPodcasts() == null) {
            message = "You can't load an empty audio collection!";
        } else if (!user.isSuccessfulSelect()) {
            message = "Please select a source before attempting to load.";
        } else {
            message = "Playback loaded successfully.";
            user.setSuccessfullLoad(true);
        }
        node.put("message", message);
        this.output.add(node);
        }
        /**
         * Method that adds the output for the status command
         */
        public void addOutputStatusMapper(
                final UserClass user, String name, final int remTime, final String repM) {
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
    /**
     * method that adds the output for the play/pause command
     */
    public void addOutputPlayPauseMapper(final UserClass user) {
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
    /**
     * Method that adds the output for the create a playlist command
     */
    public void addOutputCreatePlaylist(final UserClass user, final boolean found) {
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
    /**
     * Method that adds the output for the add/remove in playlist command
     */
    public void
    addOutputAddRemoveInPlaylistMapper(final UserClass user, final String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "addRemoveInPlaylist");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }
    /**
     * Method that adds the output for the show playlist command
     */
    public void addOutputShowPlaylistsMapper(final UserClass user) {
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
    /**
     * Method that adds the output for the show preferred songs command
     */
    public void addOutputShowPreferredSongsMapper(final UserClass user) {
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
    /**
     * Method that adds the output for the like songs command
     */
    public void addOutputLikeMapper(final UserClass user, final String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "like");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }

    /**
     * Method that adds the output for the follow playlist command
     */
    public void addOutputFollow(final UserClass user, final String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "follow");
        node.put("message", message);
        node.put("timestamp", user.getLastTimestamp());
        node.put("user", user.getUsername());
        this.output.add(node);
    }
    /**
     * Method that adds the output for the switch visibility command
     */
    public void addOutputSwitchVisibility(final UserClass user, final String message) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "switchVisibility");
        node.put("user", user.getUsername());
        node.put("timestamp", user.getLastTimestamp());
        node.put("message", message);
        this.output.add(node);
    }
    /**
     * Method that adds the output for the top 5 playlists command
     */
    public void addOutputTop5PlaylistsMapper(
            final Integer timestamp, final ArrayList<Playlist> top5Playlists) {
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
    /**
     * Method that adds the output for the top 5 songs command
     */
    public void addOutputTop5SongsMapper(
            final Integer integer, final ArrayList<String> top5Songs) {
        ObjectNode node = this.mapper.createObjectNode();
        node.put("command", "getTop5Songs");

        node.put("result", this.mapper.valueToTree(top5Songs));
        node.put("timestamp", integer);
        this.output.add(node);
    }
    /**
     * Method that makes a deep copy of the users
     */
    public void copyAllUsers(final ArrayList<UserInput> users) {
        this.users = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            this.users.add(new UserClass());
            this.users.get(i).setUsername(users.get(i).getUsername());
            this.users.get(i).setAge(users.get(i).getAge());
            this.users.get(i).setCity(users.get(i).getCity());
        }
    }
   /**
     * Method that sets the output
     */
    public void setOutput(final ArrayNode output) {
        this.output = output;
    }
    /**
     * Method that sets the mapper
     */
    public void setMapper(final ObjectMapper mapper) {
        this.mapper = mapper;
    }
    /**
     * Method that returns the users
     */
    public ArrayList<UserClass> getUsers() {
        return users;
    }
    /**
     * Method that returns the playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
    /**
     * Method that sets the playlists
     */
    public void setPlaylists(final ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }
    /**
     * Method that adds a user
     */
    public void addInPlaylists(final Playlist playlist) {
        this.playlists.add(playlist);
    }
    /**
     * Method that return the list og liked songs
     */
    public ArrayList<String> getLikedSongs() {
        return likedSongs;
    }
    /**
     * Method that sets the list of liked songs
     */
    public void setLikedSongs(final ArrayList<String> likedSongs) {
        this.likedSongs = likedSongs;
    }
    /**
     * Method that returns the list of likes
     */
    public ArrayList<Integer> getLikes() {
        return likes;
    }
    /**
     * Method that sets the list of likes
     */
    public void setLikes(final ArrayList<Integer> likes) {
        this.likes = likes;
    }
    /**
     * Method that adds a like to a song
     */
    public void addLikedSong(final String songName) {
        likedSongs.add(songName);
        likes.add(1); // Initialize with 1 like
    }
    /**
     * Method that removes a like from a song
     */
    public void removeLikedSong(final String songName) {
        int index = likedSongs.indexOf(songName);
        if (index != -1) {
            likedSongs.remove(index);
            likes.remove(index);
        }
    }
    /**
     * Method that adds a like to a song and also
     * adds the song if it doesn't exist
     */
    public void addLikeToSong(final String songName) {
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
    /**
     * Method that removes a like from a song and also
     * removes the song if it reaches 0 likes
     */
    public void removeLikeFromSong(final String songName) {
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
