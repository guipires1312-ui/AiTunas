import java.util.List;

interface Playlist{
    public String getName();
    public Time getDuration();
    public List<Artist> getArtists();
    public List<Album.Song> getSongs();
}