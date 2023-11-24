package main;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Search extends Command {
    public void setSearch(Command comm) {
        this.setCommand(comm.getCommand());
        this.setUsername(comm.getUsername());
        this.setTimestamp(comm.getTimestamp());
        this.setType(comm.getType());
        this.setFilters(comm.getFilters());
    }
    public void resetSearch(Player player, String username) {
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
    public void run(Player player) {
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

        }

    }

    private void searchSong(Player player) {
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

        if (songs.size() > 5) {
            songs = new ArrayList<>(songs.subList(0, 5));
        }
        player.setSerchedSongsUser(this.getUsername(), songs, this.getTimestamp());
    }

    public ArrayList<SongInput> searchSongName(ArrayList<SongInput> songs, String name) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getName().startsWith(name)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }

    public ArrayList<SongInput> searchSongLyrics(ArrayList<SongInput> songs, String Lyrics) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getLyrics().contains(Lyrics) || song.getLyrics().toUpperCase().contains(Lyrics)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }

    public ArrayList<SongInput> searchSongGenre(ArrayList<SongInput> songs, String Genre) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getGenre().toLowerCase().contains(Genre) || song.getGenre().contains(Genre)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }


    public ArrayList<SongInput> searchSongAritst(ArrayList<SongInput> songs, String artist) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getArtist().equals(artist)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }

    public ArrayList<SongInput> searchSongAlbum(ArrayList<SongInput> songs, String album) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getAlbum().equals(album)) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }

    public ArrayList<SongInput> searchSongTags(ArrayList<SongInput> songs, ArrayList<String> tags) {
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

    public ArrayList<SongInput> searchSongReleaseYear(ArrayList<SongInput> songs, String relY) {
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

    public ArrayList<SongInput> searchSongReleaseYearBigger(ArrayList<SongInput> songs, int relY) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getReleaseYear() > relY) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }
    public ArrayList<SongInput> searchSongReleaseYearSmaller(ArrayList<SongInput> songs, int relY) {
        ArrayList<SongInput> songsOutput = new ArrayList<>();
        for (SongInput song : songs) {
            if (song.getReleaseYear() < relY) {
                songsOutput.add(song);

            }
        }
        return songsOutput;
    }

    public void searchPodcast(Player player) {
        ArrayList<PodcastInput> podcasts = player.getLib().getPodcasts();
        if (this.getFilters().getName() != null) {
            podcasts = this.searchPodcastName(podcasts, this.getFilters().getName());
        } else if (this.getFilters().getOwner() != null) {
            podcasts = this.searchPodcastOwner(podcasts, this.getFilters().getOwner());
        }
        if (podcasts.size() > 5) {
            podcasts = new ArrayList<>(podcasts.subList(0, 5));
        }
        player.setSearchedPodcastsUser(this.getUsername(), podcasts, this.getTimestamp());
    }
    public ArrayList<PodcastInput> searchPodcastName(ArrayList<PodcastInput> podcasts, String n) {
        ArrayList<PodcastInput> podcastsOutput = new ArrayList<>();
        for (PodcastInput podcast : podcasts) {
            if (podcast.getName().startsWith(n)) {
                podcastsOutput.add(podcast);

            }
        }
        return podcastsOutput;
    }
    public ArrayList<PodcastInput> searchPodcastOwner(ArrayList<PodcastInput> pods, String o) {
        ArrayList<PodcastInput> podcastsOutput = new ArrayList<>();
        for (PodcastInput podcast : pods) {
            if (podcast.getOwner().contains(o)) {
                podcastsOutput.add(podcast);

            }
        }
        return podcastsOutput;
    }
    public void searchPlaylist(Player player) {
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
        if (playlists.size() > 5) {
            playlists = new ArrayList<>(playlists.subList(0, 5));
        }
        user.setSearchedPlaylists(playlists);
        user.setSearched(true);
        user.setLastTimestamp(Integer.valueOf(this.getTimestamp()));
        player.addOutputSearchPlaylistMapper(user);
    }
    public ArrayList<Playlist> searchPlaylistName(ArrayList<Playlist> playlists, String name) {
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
    public ArrayList<Playlist> searchPlaylistOwner(ArrayList<Playlist> playlists, String owner) {
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
