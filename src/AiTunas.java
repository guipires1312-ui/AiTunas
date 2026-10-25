import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class AiTunas {

    private List<Album> albums;
    private List<Artist> artists;
    private List<Playlist> playlists;

    public AiTunas() {
        albums = new ArrayList<Album>();
        artists = new ArrayList<Artist>();
    }

    public static AiTunas load(File dir) {
        AiTunas aitunas = new AiTunas();
        File[] albumsArray = dir.listFiles();
        Album album;
        for (int i = 0; i < albumsArray.length; i++) {
            album = Album.load(albumsArray[i], aitunas);
            aitunas.albums.add(album);
        }
        return aitunas;
    }

    public List<Album> getAllAlbums() {
        this.albums.sort((a1, a2) -> {
            return a1.getYear() - a2.getYear();
        });
        return this.albums;
    }

    public List<Artist> getAllArtists() {
        this.artists.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
        return this.artists;
    }

    public boolean hasArtist(String newArtist) {
        for (Artist artist : artists) {
            if (artist.getName().equals(newArtist)) {
                return true;
            }
        }
        return false;
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public Artist findArtist(String artistName) {
        for (Artist artist : artists) {
            if (artist.getName().equals(artistName)) {
                return artist;
            }
        }
        return null;
    }

    public List<String> getArtistsForSong(Album.Song song) {
        for (Album album : this.albums) {
            if (album.getSongs().contains(song)) {
                return album.getArtists();
            }
        }
        return new ArrayList<>();

    }

    public class ManualPlaylist implements Playlist {
        private List<Album.Song> songs;
        private List<Artist> artists;
        private String name;


        public ManualPlaylist(String name) {
            this.name = name;
            songs = new ArrayList<Album.Song>();
            artists = new ArrayList<Artist>();

        }

        private List<String> artistsListToStrList() {
            List<String> artistsStr = new ArrayList<String>();
            for (Artist artist : this.artists) {
                artistsStr.add(artist.getName());
            }
            return artistsStr;
        }

        public List<Artist> getNewArtists(Album.Song song) {
            List<String> songArtists = getArtistsForSong(song);
            List<String> playlistArtists = artistsListToStrList();
            List<String> newArtistsStr = new ArrayList<>();
            List<Artist> newArtists = new ArrayList<>();
            for (String artistName : songArtists) {
                if (!playlistArtists.contains(artistName)) {
                    newArtistsStr.add(artistName);
                }
            }
            for (String artistName : newArtistsStr) {
                newArtists.add(findArtist(artistName));
            }
            return newArtists;
        }


        public void addSong(Album.Song song) {
            List<Artist> newArtists = getNewArtists(song);
            if (newArtists != null) {
                this.artists.addAll(newArtists);
            }
            this.songs.add(song);
        }

        public String getName() {
            return this.name;
        }

        public Time getDuration() {
            Time time = new Time(0, 0);
            for (Album.Song song : songs) {
                time = time.add(song.getDuration());
            }
            return time;
        }

        public List<Artist> getArtists() {
            return this.artists;
        }

        public List<Album.Song> getSongs() {
            return this.songs;
        }
    }

    public class ArtistPlaylist implements Playlist{

        private List<Album.Song> songs = new ArrayList<>();
        private String artistName;
        private Artist artist;

        public ArtistPlaylist(String artistName) {
            this.artistName = artistName;
            this.artist = findArtist(artistName);
            List<Album> albums = this.artist.getAlbums();
            for (Album album : albums) {
                List<Album.Song> songs = album.getSongs();
                this.songs.addAll(songs);
            }
        }


        public String getName() {
            return artistName;

        }

        public Time getDuration() {
            Time time = new Time(0, 0);
            for (Album.Song song : this.songs) {
                time = time.add(song.getDuration());
            }
            return time;
        }

        public List<Artist> getArtists() {
            List<Artist> artistList = new ArrayList<>();
            artistList.add(this.artist);
            return artistList;
        }

        public List<Album.Song> getSongs() {
            return this.songs;
        }
    }
}