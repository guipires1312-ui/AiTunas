import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Album {
    private final String albumName;
    private final int year;
    private final List<String> artists;
    private final List<Song> songs;

    public Album(String albumName, int year, List<String> artists, List<Song> songs) {
        this.albumName = albumName;
        this.year = year;
        this.artists = artists;
        this.songs = songs;
    }

    public String getName() {
        return this.albumName;
    }

    public List<String> getArtists() {
        return this.artists;
    }

    public List<Song> getSongs() {
        sortByTrack();
        return this.songs;
    }

    public int getYear() {
        return this.year;
    }

    public Time getDuration() {
        Time duration = new Time(0, 0);
        for (Song song : this.songs) {
            duration = duration.add(song.getDuration());
        }
        return duration;
    }

    public static Album load(File file, AiTunas aitunas) {

        String albumName;
        int year;
        List<String> albumArtists = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            albumName = scanner.nextLine();
            year = Integer.parseInt(scanner.nextLine());
            String artist = scanner.nextLine();
            Artist newArtist;
            while ((!"".equals(artist))) {
                if (!aitunas.hasArtist(artist)) {
                    newArtist = new Artist(artist, new ArrayList<Album>());
                    aitunas.addArtist(newArtist);
                }
                artists.add(aitunas.findArtist(artist));
                albumArtists.add(artist);
                artist = scanner.nextLine();
            }

            String line;
            String durationStr;
            String songName;
            Time duration;
            Song song;
            int spaceIndex;
            int trackNumber = 1;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                spaceIndex = line.indexOf(" ");
                durationStr = line.substring(0, spaceIndex);
                songName = line.substring(spaceIndex + 1);
                duration = new Time(durationStr);
                song = new Song(songName, duration, trackNumber);
                songs.add(song);
                trackNumber++;
            }
            scanner.close();
            Album album = new Album(albumName, year, albumArtists, songs);

            for (Artist a : artists) {
                a.addAlbum(album);
            }


            return album;


        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        }
        return null;
    }


    public void sortByTrack() {
        this.songs.sort((song1, song2) -> song1.getTrackNumber() - song2.getTrackNumber());
    }

    public void sortByDuration() {
        this.songs.sort((song1, song2) -> {
            if (song1.getDuration().isSmaller(song2.getDuration())) return -1;
            if (song2.getDuration().isSmaller(song1.getDuration())) return 1;
            return 0;
        });
    }

    public void sortByAlphabeticOrder() {
        this.songs.sort((song1, song2) -> {
            char[] song1CharArray = song1.getName().toCharArray();
            char[] song2CharArray = song2.getName().toCharArray();
            int length;
            if (song1CharArray.length < song2CharArray.length) {
                length = song1CharArray.length;
            } else {
                length = song2CharArray.length;
            }
            for (int i = 0; i < length; i++) {
                if (Character.toLowerCase(song1CharArray[i]) < Character.toLowerCase(song2CharArray[i])) {
                    return -1;
                } else if (Character.toLowerCase(song2CharArray[i]) < Character.toLowerCase(song1CharArray[i])) {
                    return 1;
                }
            }
            if (song1CharArray.length == song2CharArray.length) {
                return 0;
            } else if (song1CharArray.length > song2CharArray.length) {
                return 1;
            } else {
                return -1;
            }
        });
    }


    public static class Song {
        String songName;
        Time duration;
        int trackNumber;

        public Song(String songName, Time duration, int trackNumber) {
            this.songName = songName;
            this.duration = duration;
            this.trackNumber = trackNumber;
        }

        public String getName() {
            return this.songName;
        }

        public Time getDuration() {
            return this.duration;
        }

        public int getTrackNumber() {
            return this.trackNumber;
        }
    }

}