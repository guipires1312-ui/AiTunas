import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       TESTE COMPLETO DO SISTEMA AITUNAS         ");
        System.out.println("=================================================\n");

        // ----------------------------------------------------
        // 1. TESTE DA CLASSE TIME
        // ----------------------------------------------------
        System.out.println("--- 1. Teste de Operações com a Classe Time ---");
        Time t1 = new Time(3, 45);
        Time t2 = new Time("02:15");

        System.out.println("t1: " + t1 + " | t2: " + t2);
        System.out.println("Segundos totais de t1: " + t1.totalSeconds() + "s");
        System.out.println("Soma (3:45 + 2:15): " + t1.add(t2));
        System.out.println("Subtração (3:45 - 2:15): " + t1.sub(t2));
        System.out.println("t2 é menor que t1? " + t2.isSmaller(t1));
        System.out.println("t1 é maior que t2? " + t1.isBigger(t2));
        System.out.println();

        // ----------------------------------------------------
        // 2. CARREGAMENTO E IMPRESSÃO DE TODOS OS ÁLBUNS DA PASTA
        // ----------------------------------------------------
        System.out.println("--- 2. Carregamento e Impressão Geral (AiTunas.load) ---");
        File pastaAlbuns = new File("Albuns");

        if (!pastaAlbuns.exists() || !pastaAlbuns.isDirectory()) {
            System.err.println("ERRO: A pasta 'Albuns' não foi encontrada na raiz do projeto!");
            return;
        }

        AiTunas aitunas = AiTunas.load(pastaAlbuns);

        System.out.println("Total de Álbuns Carregados: " + aitunas.getAllAlbums().size());
        System.out.println("Total de Artistas Registados: " + aitunas.getAllArtists().size());
        System.out.println();

        System.out.println("--- Lista Completa de Álbuns e Músicas Lidos da Pasta ---");
        for (Album a : aitunas.getAllAlbums()) {
            System.out.println("\nÁlbum: " + a.getName() + " (" + a.getYear() + ")");
            System.out.println("Duração Total: " + a.getDuration());
            System.out.println("Artistas: " + a.getArtists());
            System.out.println("Músicas:");
            for (Album.Song s : a.getSongs()) {
                System.out.println("  Faixa " + s.getTrackNumber() + ": " + s.getName() + " [" + s.getDuration() + "]");
            }
        }
        System.out.println();

        // ----------------------------------------------------
        // 3. TESTE DE TODAS AS ORDENAÇÕES NO ÁLBUM (LAMBDAS)
        // ----------------------------------------------------
        if (!aitunas.getAllAlbums().isEmpty()) {
            Album album = aitunas.getAllAlbums().get(0);
            System.out.println("--- 3. Teste de Ordenação no Álbum: '" + album.getName() + "' ---");

            System.out.println("\n-> Ordenar por Número de Faixa (sortByTrack):");
            album.sortByTrack();
            imprimirMusicas(album);

            System.out.println("\n-> Ordenar por Ordem Alfabética (sortByAlphabeticOrder):");
            album.sortByAlphabeticOrder();
            imprimirMusicas(album);

            System.out.println("\n-> Ordenar por Duração (sortByDuration):");
            album.sortByDuration();
            imprimirMusicas(album);
            System.out.println();
        }

        // ----------------------------------------------------
        // 4. TESTE DE PESQUISA E ASSOCIAÇÃO DE ARTISTAS
        // ----------------------------------------------------
        System.out.println("--- 4. Teste de Pesquisa de Artistas (hasArtist / findArtist) ---");
        if (!aitunas.getAllArtists().isEmpty()) {
            String nomeArtista = aitunas.getAllArtists().get(0).getName();

            System.out.println("Existe o artista '" + nomeArtista + "'? " + aitunas.hasArtist(nomeArtista));

            Artist artistaEncontrado = aitunas.findArtist(nomeArtista);
            if (artistaEncontrado != null) {
                System.out.println("Artista recuperado: " + artistaEncontrado.getName());
                System.out.println("Álbuns associados a este artista:");
                for (Album a : artistaEncontrado.getAlbums()) {
                    System.out.println("  - " + a.getName() + " (" + a.getYear() + ")");
                }
            }
        }

        System.out.println("\nPesquisa por artista inexistente ('Artista Desconhecido'):");
        System.out.println("hasArtist: " + aitunas.hasArtist("Artista Desconhecido"));
        System.out.println("findArtist: " + aitunas.findArtist("Artista Desconhecido"));

// ----------------------------------------------------
// 5. TESTE DA MANUAL PLAYLIST
// ----------------------------------------------------
        System.out.println("\n--- 5. Teste da ManualPlaylist ---");
        if (!aitunas.getAllAlbums().isEmpty()) {

            AiTunas.ManualPlaylist playlist = aitunas.new ManualPlaylist("As Minhas Favoritas");

            Album album1 = aitunas.getAllAlbums().get(0);
            if (!album1.getSongs().isEmpty()) {
                Album.Song musica1 = album1.getSongs().get(0);
                System.out.println("-> Adicionando a música: " + musica1.getName());
                playlist.addSong(musica1);

                if (album1.getSongs().size() > 1) {
                    Album.Song musica2 = album1.getSongs().get(1);
                    System.out.println("-> Adicionando a música: " + musica2.getName());
                    playlist.addSong(musica2);
                }
            }

            if (aitunas.getAllAlbums().size() > 1) {
                Album album2 = aitunas.getAllAlbums().get(1);
                if (!album2.getSongs().isEmpty()) {
                    Album.Song musicaAlbum2 = album2.getSongs().get(0);
                    System.out.println("-> Adicionando música de outro álbum: " + musicaAlbum2.getName());
                    playlist.addSong(musicaAlbum2);
                }
            }

            System.out.println("\n--- Estado Final da Playlist ---");
            System.out.println("Nome da Playlist: " + playlist.getName());
            System.out.println("Duração Total: " + playlist.getDuration());

            System.out.println("\nMúsicas na Playlist:");
            for (Album.Song s : playlist.getSongs()) {
                System.out.println("  - " + s.getName() + " [" + s.getDuration() + "]");
            }

            System.out.println("\nArtistas na Playlist (sem duplicados):");
            for (Artist a : playlist.getArtists()) {
                System.out.println("  - " + a.getName());
            }
        }


// ----------------------------------------------------
// 6. TESTE DA ARTIST PLAYLIST
// ----------------------------------------------------
        System.out.println("\n--- 6. Teste da ArtistPlaylist ---");
        if (!aitunas.getAllArtists().isEmpty()) {

            String nomeArtista = aitunas.getAllArtists().get(0).getName();
            System.out.println("-> Criando playlist para o artista: " + nomeArtista);

            AiTunas.ArtistPlaylist artistPlaylist = aitunas.new ArtistPlaylist(nomeArtista);

            System.out.println("\n--- Estado Final da ArtistPlaylist ---");
            System.out.println("Nome da Playlist: " + artistPlaylist.getName());
            System.out.println("Duração Total: " + artistPlaylist.getDuration());

            System.out.println("\nMúsicas na Playlist:");
            for (Album.Song s : artistPlaylist.getSongs()) {
                System.out.println("  - " + s.getName() + " [" + s.getDuration() + "]");
            }

            System.out.println("\nArtistas na Playlist:");
            for (Artist a : artistPlaylist.getArtists()) {
                System.out.println("  - " + a.getName());
            }
        }


        System.out.println("\n=================================================");
        System.out.println("          TODOS OS TESTES CONCLUÍDOS!            ");
        System.out.println("=================================================");
    }

    private static void imprimirMusicas(Album album) {
        for (Album.Song s : album.getSongs()) {
            System.out.println("  Faixa " + s.getTrackNumber() + " | " + s.getName() + " [" + s.getDuration() + "]");
        }
    }


}