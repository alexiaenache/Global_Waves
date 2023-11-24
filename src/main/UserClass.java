package main;

import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the information about an user
 */
public class UserClass {
        private String username;
        private int age;
        private String city;
        private int lastTimestamp;
        private boolean successfulSelect;
        private boolean successfullLoad;

        private ArrayList<SongInput> searchedSongs;
        private ArrayList<PodcastInput> searchedPodcasts;
        private ArrayList<Playlist> searchedPlaylists;
        private String selectedSearch;
        private boolean shuffle = false;
        private int repeat = 0;
        private boolean paused = false;
        private boolean searched = false;
        private int lastPlayPause = 0;
        private int remainedTime = 0;
        private int lastPlay = 0;
        private SongInput loadedSong;
        private PodcastInput loadedPodcast;
        private Playlist loadedPlaylist;
        private ArrayList<SongInput> likedSongs;
        private ArrayList<Playlist> playlists;
        private Integer playedPodcastForTime = 0;
        /**
         * Method that returns the state of the search
         */
        public boolean isSearched() {
        return searched;
    }
        /**
         * Method that sets the state of the search
         */
        public void setSearched(final boolean searched) {
        this.searched = searched;
    }
        /**
         * Method that returns the amount of time a podcast was played
         */
        public Integer getPlayedPodcastForTime() {
        return playedPodcastForTime;
    }
        /**
         * Method that sets the amount of time a podcast was played
         */
        public void setPlayedPodcastForTime(final Integer playedPodcastForTime) {
        this.playedPodcastForTime = playedPodcastForTime; }
        /**
         * Method that resets the serached songs
         */
        public void removeSearchedSongs() {
            this.searchedSongs = null;
        }
        /**
         * Method that resets the searched podcasts
         */
        public void removeSearchedPodcasts() {
            this.searchedPodcasts = null;
        }
        /**
         * Method that returns the searched songs
         */
        public ArrayList<SongInput> getSearchedSongs() {
        return searchedSongs;
    }
        /**
         * Method that returns the searched podcasts
         */
        public ArrayList<PodcastInput> getSearchedPodcasts() {
        return searchedPodcasts;
    }
        /**
         * Method that returns the selected input
         */
        public String getSelectedSearch() {
        return selectedSearch;
    }
        /**
         * Method that sets the selected input
         */
        public void setSelectedSearch(final String selectedSearch) {
        this.selectedSearch = selectedSearch;
    }
        /**
         * Method that sets the selcted input and the timestamp
         */
        public void selectedSearch(final String selctedSaerch, final String timestamp) {
        this.selectedSearch = selctedSaerch;
        this.lastTimestamp = Integer.valueOf(timestamp); }
        /**
         * Method that returns the loaded podcast
         */
        public PodcastInput getLoadedPodcast() {
        return loadedPodcast;
    }
        /**
         * Method that sets the loaded playlist
         */
        public void setLoadedPodcast(final PodcastInput loadedPodcast) {
            this.loadedPodcast = loadedPodcast; }
        /**
         * Method that returns the loaded playlist
         */
        public Playlist getLoadedPlaylist() {
            return loadedPlaylist; }
        /**
         * Method that sets the loaded playlist
         */
        public void setLoadedPlaylist(final Playlist loadedPlaylist) {
        this.loadedPlaylist = loadedPlaylist; }
        /**
         * Method that returns the searched playlists
         */
        public ArrayList<Playlist> getSearchedPlaylists() {
        return searchedPlaylists; }
        /**
         * Method that sets the searched playlists
         */
        public void setSearchedPlaylists(final ArrayList<Playlist> searchedPlaylists) {
        this.searchedPlaylists = searchedPlaylists; }
        /**
         * Method that returns the loaded song
         */
        public SongInput getLoadedSong() {
        return loadedSong; }
        /**
         * Method that adds a song to the liked songs
         */
        public void addLikedSong(final SongInput song) {
        this.likedSongs.add(song); }
        /**
         * Method that removes a song from the liked songs
         */
        public void removeLikedSong(final SongInput song) {
        this.likedSongs.remove(song); }
        /**
         * Method that returns the liked songs
         */
        public ArrayList<SongInput> getLikedSongs() {
        return likedSongs; }
        /**
         * Method that sets the liked songs
         */
        public void setLikedSongs(final ArrayList<SongInput> likedSongs) {
        this.likedSongs = likedSongs; }
        /**
         * Method that sets the loaded song
         */
        public void setLoadedSong(final SongInput loadedSong) {
        this.loadedSong = loadedSong; }
        /**
         * Method that adds a playlist
         */
        public void addPlaylist(final Playlist playlist) {
        this.playlists.add(playlist); }
        /**
         * Method that removes a playlist
         */
        public ArrayList<Playlist> getPlaylists() {
        return playlists; }
        /**
         * Method that sets the playlists
         */
        public void setPlaylists(final ArrayList<Playlist> playlists) {
        this.playlists = playlists; }
        /**
         * Method that returns the remained time
         */
        public int getRemainedTime() {
        return remainedTime; }
        /**
         * Method that returns the last play
         */

    public int getLastPlay() {
        return lastPlay;
    }
    /**
     * Method that sets the last play
     */
    public void setLastPlay(final int lastPlay) {
        this.lastPlay = lastPlay;
    }
    /**
     * Method that sets the remained time
     */
    public void setRemainedTime(final int remainedTime) {
        this.remainedTime = remainedTime;
    }
    /**
     * Method that returns the state of the pause
     */

    public boolean isPaused() {
        return paused;
    }
    /**
     * Method that sets the state of the pause
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }
    /**
     * Method that returns the state of the shuffle
     */
    public boolean isShuffle() {
        return shuffle;
    }
    /**
     * Method that sets the state of the shuffle
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }
    /**
     * Method that returns the state of the repeat
     */
    public int getRepeat() {
        return repeat;
    }
    /**
     * Method that sets the state of the repeat
     */
    public void setRepeat(final int repeat) {
        this.repeat = repeat;
    }
    /**
     * Method that returns the state of teh load
     */
    public boolean isSuccessfullLoad() {
        return successfullLoad;
    }
    /**
     * Method that sets the state of the load
     */
    public void setSuccessfullLoad(final boolean successfullLoad) {
        this.successfullLoad = successfullLoad;
    }
    /**
     * Method that returns the state of the select
     */
    public boolean isSuccessfulSelect() {
        return successfulSelect;
    }
    /**
     * Method that sets the state of the select
     */
    public void setSuccessfulSelect(final boolean successfulSelect) {
        this.successfulSelect = successfulSelect;
    }
    /**
     * Method that returns the last timestamp
     */
    public int getLastTimestamp() {
        return lastTimestamp;
    }
    /**
     * Method that sets the last timestamp
     */
    public void setLastTimestamp(final int lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
    /**
     * Method that returns the searched podcasts
     */
    public void setSearchedPodcasts(final ArrayList<PodcastInput> searchedPodcasts) {
        this.searchedPodcasts = searchedPodcasts;
    }

    /**
     * Method that sets the searched podcasts
     */
        public void setSearchedSongs(final ArrayList<SongInput> searchedSongs) {
            this.searchedSongs = searchedSongs;
        }
        /**
         * constructor
         */
        public UserClass() {
        }
        /**
         * Method that returns the username
         */
        public String getUsername() {
            return username;
        }
        /**
         * Method that sets the username
         */
        public void setUsername(final String username) {
            this.username = username;
        }
        /**
         * Method that returns the age
         */
        public int getAge() {
            return age;
        }
        /**
         * Method that sets the age
         */
        public void setAge(final int age) {
            this.age = age;
        }
        /**
         * Method that returns the city
         */
        public String getCity() {
            return city;
        }
        /**
         * Method that sets the city
         */
        public void setCity(final String city) {
            this.city = city;
        }

}

