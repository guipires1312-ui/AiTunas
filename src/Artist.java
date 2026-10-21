import java.util.List;

public class Artist {

    private String name;
    private List<Album> albums;

    public Artist(String name, List<Album> albums) {
        this.name = name;
        this.albums = albums;
    }

    public String getName() {
        return this.name;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<Album> getAlbums() {
        return this.albums;
    }

}