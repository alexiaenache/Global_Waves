package main;

import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class that contains the methods for searching songs, podcasts and playlists
 */
public class Search extends Command {
    public static final int MAX = 5;
    /**
     * Method that sets the command
     */
    public void setSearch(final Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setType(comm.getType());
        this.setFilters(comm.getFilters());
    }
    /**
     * Method that resets the search
     */
    public void resetSearch(final Player player, final String username) {
        for (int i = 0; i < player.getUsers().size(); i++) {
            if (player.getUsers().get(i).getUsername().equals(username)) {
                UserClass user = player.getUsers().get(i);

                user.setSearchedSongs(new ArrayList<>());
                user.setSearchedPodcasts(new ArrayList<>());
                user.setSearchedPlaylists(new ArrayList<>());
                user.setSuccessfulSelect(false);
                user.setSuccessfullLoad(false);
                user.setLoadedSong(null);
            }
        }
    }
    /**
     * Method that runs the command
     */
    public void run(final Player player) {
        resetSearch(player, getUsername());
        switch (this.getType()) {
            case "song":
                searchSong(player);
                break;
            case "playlist":
                searchPlaylist(player);
                break;
            case "podcast":
                searchPodcast(player);
                break;
                default:
                    break;

        }

    }
    /**
     * Method that searches a song
     */
    private void searchSong(final Player player) {
        ArrayList<SongInput> songs = player.getLib().getSongs();
        if (this.getFilters().getName() != null) {
            songs = this.searchSongName(songs, this.getFilters().getName());
        }
        if (this.getFilters().getTags() != null) {
            songs = this.searchSongTags(songs, this.getFilters().getTags());

        }
        if (this.getFilters().getLyrics() != null) {
            songs = this.searchSongLyrics(songs, this.getFilters().getLyrics());

        }
        if (this.getFilters().getArtist() != null) {
            songs = this.searchSongAritst(songs, this.getFilters().getArtist());

        }
        if (this.getFilters().getAlbum() != null) {
            songs = this.searchSongAlbum(songs, this.getFilters().getAlbum());

        }
        if (this.getFilters().getReleaseYear() != null) {
            songs = this.searchSongReleaseYear(songs, this.getFilters().getReleaseYear());

        }
        if (this.getFilters().getGenre() != null) {
            songs = this.searchSongGenre(songs, this.getFilters().getGenre());
        }

        if (songs.size() > MAX) {
            songs = new ArrayList<>(songs.subList(0, MAX));
        }
        player.setSerchedSongsUser(this.getUsername(), songs, this.getTimestamp());
    }
    /**
     * Method that searches a song by name
     */
    public ArrayList<SongInput>
    searchSongName(final ArrayList<SongInput> songs, final String name) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getName().startsWith(name)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by lyrics
     */
    public ArrayList<SongInput>
    searchSongLyrics(final ArrayList<SongInput> songs, final String lyrics) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getLyrics().contains(lyrics)
                    || song.getLyrics().toUpperCase().contains(lyrics)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by genre
     */
    public ArrayList<SongInput>
    searchSongGenre(final ArrayList<SongInput> songs, final String genre) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getGenre().toLowerCase().contains(genre)
                    || song.getGenre().contains(genre)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by artist
     */
    public ArrayList<SongInput>
    searchSongAritst(final ArrayList<SongInput> songs, final String artist) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getArtist().equals(artist)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by album
     */
    public ArrayList<SongInput>
    searchSongAlbum(final ArrayList<SongInput> songs, final String album) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getAlbum().equals(album)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by tags
     */
    public ArrayList<SongInput>
    searchSongTags(final ArrayList<SongInput> songs, final ArrayList<String> tags) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            boolean hasAllTags = true;

            for (String tag : tags) {
                if (!song.getTags().contains(tag)) {
                    hasAllTags = false;
                    break; // Break if any tag is not found
                }
            }

            if (hasAllTags) {
                songsOutput.add(song);
            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by release year
     */
    public ArrayList<SongInput>
    searchSongReleaseYear(final ArrayList<SongInput> songs, final String relY) {
        String numericalPart = relY.replaceAll("[^0-9]", "");
        Integer number = Integer.parseInt(numericalPart);
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        if (!relY.isEmpty() && relY.charAt(0) == '<') {
            songsOutput = this.searchSongReleaseYearSmaller(songs, number);
        } else if (!relY.isEmpty() && relY.charAt(0) == '>') {
            songsOutput = this.searchSongReleaseYearBigger(songs, number);

        }
        return songsOutput;
    }
    /**
     * Method that searches a song by release year and returns
     * the songs with release year bigger than the given one
     */
    public ArrayList<SongInput>
    searchSongReleaseYearBigger(final ArrayList<SongInput> songs, final int relY) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getReleaseYear() > relY) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a song by release year and returns
     * the songs with release year smaller than the given one
     */
    public ArrayList<SongInput> searchSongReleaseYearSmaller(
            final ArrayList<SongInput> songs, final int relY) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getReleaseYear() < relY) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    /**
     * Method that searches a podcast
     */
    public void searchPodcast(final Player player) {
        ArrayList<PodcastInput> podcasts = player.getLib().getPodcasts();
        if (this.getFilters().getName() != null) {
            podcasts = this.searchPodcastName(podcasts, this.getFilters().getName());
        } else if (this.getFilters().getOwner() != null) {
            podcasts = this.searchPodcastOwner(podcasts, this.getFilters().getOwner());
        }
        if (podcasts.size() > MAX) {
            podcasts = new ArrayList<>(podcasts.subList(0, MAX));
        }
        player.setSearchedPodcastsUser(this.getUsername(), podcasts, this.getTimestamp());
    }
    /**
     * Method that searches a podcast by name
     */
    public ArrayList<PodcastInput>
    searchPodcastName(final ArrayList<PodcastInput> podcasts, final String n) {
        ArrayList<PodcastInput> podcastsOutput = new ArrayList<>();
        for (PodcastInput podcast : podcasts) {
            if (podcast.getName().startsWith(n)) {
                podcastsOutput.add(podcast);

            }
        }
        return podcastsOutput;
    }
    /**
     * Method that searches a podcast by owner
     */
    public ArrayList<PodcastInput>
    searchPodcastOwner(final ArrayList<PodcastInput> pods, final String o) {
        ArrayList<PodcastInput> podcastsOutput = new ArrayList<>();
        for (PodcastInput podcast : pods) {
            if (podcast.getOwner().contains(o)) {
                podcastsOutput.add(podcast);

            }
        }
        return podcastsOutput;
    }
    /**
     * Method that searches a playlist
     */
    public void searchPlaylist(final Player player) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        int n = player.getUsers().size();
        UserClass user = null;
        for (int i = 0; i < n; i++) {
            if (this.getUsername().equals(player.getUsers().get(i).getUsername())) {
                user = player.getUsers().get(i);
            }

        }
        playlists = player.getPlaylists();
        if (this.getFilters().getName() != null) {
            playlists = this.searchPlaylistName(playlists, this.getFilters().getName());
        } else if (this.getFilters().getOwner() != null) {
            playlists = this.searchPlaylistOwner(playlists, this.getFilters().getOwner());
        }
        if (playlists.size() > MAX) {
            playlists = new ArrayList<>(playlists.subList(0, MAX));
        }
        user.setSearchedPlaylists(playlists);
        user.setSearched(true);
        user.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
        player.addOutputSearchPlaylistMapper(user);
    }
    /**
     * Method that searches a playlist by name
     */
    public ArrayList<Playlist>
    searchPlaylistName(final ArrayList<Playlist> playlists, final String name) {
        ArrayList<Playlist> playlistsOutput = new ArrayList<>();
        if (playlists != null && !playlists.isEmpty()) {
            for (Playlist playlist : playlists) {
                if (playlist.getName().startsWith(name)  && playlist.isPublic()) {
                    playlistsOutput.add(playlist);
                }
            }
        }
        return playlistsOutput;
    }
    /**
     * Method that searches a playlist by owner
     */
    public ArrayList<Playlist>
    searchPlaylistOwner(final ArrayList<Playlist> playlists, final String owner) {
        ArrayList<Playlist> playlistsOutput = new ArrayList<>();
        if (playlists != null && !playlists.isEmpty()) {
            for (Playlist playlist : playlists) {
                if (playlist.getOwner().contains(owner) && playlist.isPublic()) {
                    playlistsOutput.add(playlist);
                }
            }
        }
        return playlistsOutput;
    }
}
