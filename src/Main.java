import java.io.File;
import java.util.List;

class Main {
    public static void main(String[] args) {
        File file = new File("Album.txt");
        Album album = Album.load(file);

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                album.sortByTrack();
            } else if (i == 1) {
                album.sortByDuration();
            } else {
                album.sortByAlphabeticOrder();
            }
            System.out.println("Name of the album: " + album.getName());
            System.out.println("Year of the almbum: " + album.getYear());
            System.out.println("");
            System.out.println("");
            System.out.println("");

            List<String> artists = album.getArtists();
            for (String artist : artists) {
                System.out.println(artist);
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");

            List<Album.Song> songs = album.getSongs();
            for (Album.Song song : songs) {
                System.out.println("[" + song.getDuration().toString() + "]" + song.getName());
            }

        }
        System.out.println(album.getDuration().toString());
    }
}