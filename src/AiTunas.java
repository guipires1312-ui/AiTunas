import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class AiTunas {

    private List<Album> albums;
    private List<Artist> artists;

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
        return this.albums;
    }

    public List<Artist> getAllArtists() {
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
}