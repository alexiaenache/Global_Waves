package main;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;

public class UserClass {
        private String username;
        private int age;
        private String city;
        private int lastTimestamp;
        private boolean successfulSelect;
        private boolean successfullLoad;

        private ArrayList<SongInput> searchedSongs;
        private ArrayList<PodcastInput> searchedPodcasts;
        private ArrayList<Playlist> SearchedPlaylists;
        private String selectedSearch;
        private boolean shuffle = false;
        private int repeat = 0;
        private boolean paused = false;
        private int lastPlayPause = 0;
        private int remainedTime = 0;
        private int lastPlay = 0;
        private SongInput loadedSong;
        private PodcastInput loadedPodcast;
        private Playlist loadedPlaylist;
        private ArrayList<SongInput> likedSongs;
        private ArrayList<Playlist> playlists;
        private Integer playedPodcastForTime = 0;

    public Integer getPlayedPodcastForTime() {
        return playedPodcastForTime;
    }

    public void setPlayedPodcastForTime(Integer playedPodcastForTime) {
        this.playedPodcastForTime = playedPodcastForTime;
    }

    public ArrayList<UserClass> copyAllUsers(ArrayList<UserInput> users) {
            ArrayList<UserClass> usersOutput = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                usersOutput.get(i).setUsername(users.get(i).getUsername());
                usersOutput.get(i).setAge(users.get(i).getAge());
                usersOutput.get(i).setCity(users.get(i).getCity());
            }
            return usersOutput;
        }
        public void copyUser(UserInput user) {
            this.setUsername(user.getUsername());
            this.setAge(user.getAge());
            this.setCity(user.getCity());
        }
        public void removeSearchedSongs() {
            this.searchedSongs = null;
        }
        public void removeSearchedPodcasts() {
            this.searchedPodcasts = null;
        }
    public ArrayList<SongInput> getSearchedSongs() {
        return searchedSongs;
    }
    public ArrayList<PodcastInput> getSearchedPodcasts() {
        return searchedPodcasts;
    }

    public String getSelectedSearch() {
        return selectedSearch;
    }

    public void setSelectedSearch(String selectedSearch) {
        this.selectedSearch = selectedSearch;
    }
    public void selectedSearch(String selctedSaerch, String timestamp) {
        this.selectedSearch = selctedSaerch;
        this.lastTimestamp = Integer.valueOf(timestamp);
    }

    public PodcastInput getLoadedPodcast() {
        return loadedPodcast;
    }

    public void setLoadedPodcast(PodcastInput loadedPodcast) {
        this.loadedPodcast = loadedPodcast;
    }

    public Playlist getLoadedPlaylist() {
        return loadedPlaylist;
    }

    public void setLoadedPlaylist(Playlist loadedPlaylist) {
        this.loadedPlaylist = loadedPlaylist;
    }

    public ArrayList<Playlist> getSearchedPlaylists() {
        return SearchedPlaylists;
    }

    public void setSearchedPlaylists(ArrayList<Playlist> searchedPlaylists) {
        SearchedPlaylists = searchedPlaylists;
    }

    public SongInput getLoadedSong() {
        return loadedSong;
    }
    public void addLikedSong(SongInput song) {
        this.likedSongs.add(song); }
    public void removeLikedSong(SongInput song) {
        this.likedSongs.remove(song);
    }
    public ArrayList<SongInput> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(ArrayList<SongInput> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public void setLoadedSong(SongInput loadedSong) {
        this.loadedSong = loadedSong;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getRemainedTime() {
        return remainedTime;
    }

    public int getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(int lastPlay) {
        this.lastPlay = lastPlay;
    }

    public void setRemainedTime(int remainedTime) {
        this.remainedTime = remainedTime;
    }

    public int getLastPlayPause() {
        return lastPlayPause;
    }

    public void setLastPlayPause(int lastPlayPause) {
        this.lastPlayPause = lastPlayPause;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public boolean isSuccessfullLoad() {
        return successfullLoad;
    }

    public void setSuccessfullLoad(boolean successfullLoad) {
        this.successfullLoad = successfullLoad;
    }

    public boolean isSuccessfulSelect() {
        return successfulSelect;
    }

    public void setSuccessfulSelect(boolean successfulSelect) {
        this.successfulSelect = successfulSelect;
    }

    public int getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(int lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public void setSearchedPodcasts(ArrayList<PodcastInput> searchedPodcasts) {
        this.searchedPodcasts = searchedPodcasts;
    }
        public void setSearchedSongs(ArrayList<SongInput> searchedSongs) {
            this.searchedSongs = searchedSongs;
        }

        public UserClass() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(final int age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(final String city) {
            this.city = city;
        }

}

