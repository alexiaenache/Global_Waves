package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.LibraryInput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
//        if(!filePathInput.equals("test05_playPause_playlist_podcast.json")) return;
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        ArrayNode outputs = objectMapper.createArrayNode();

        ArrayList<Command> commands = objectMapper.readValue(new File("input/" + filePathInput), new TypeReference<ArrayList<Command>>(){ });

        ObjectMapper mapper = new ObjectMapper();
        Player p = new Player();
        p.setLib(library);
        p.setMapper(mapper);
        ArrayNode output = mapper.createArrayNode();
        p.setOutput(output);
        p.copyAllUsers(library.getUsers());
        Command c;
        for (int i = 0; i < commands.size(); i++) {
            switch (commands.get(i).getCommand()) {
                case "search":
                    c = new Search();
                    ((Search) c).setSearch(commands.get(i));
                    ((Search) c).run(p);
                    break;
                case "select":
                    c = new Select();
                    ((Select) c).setSelect(commands.get(i));
                    ((Select) c).run(p);
                    break;
                case "load":
                    c = new Load();
                    ((Load) c).setLoad(commands.get(i));
                    ((Load) c).run(p);
                    break;
                case "playPause":
                    c = new PlayPause();
                    ((PlayPause) c).setPlayPause(commands.get(i));
                    ((PlayPause) c).run(p);
                    break;
                case "status":
                    c = new Status();
                    ((Status) c).setLoad(commands.get(i));
                    ((Status) c).run(p);
                    break;
                case "createPlaylist":
                    c = new CreatePlaylist();
                    ((CreatePlaylist) c).setCreatePlaylist(commands.get(i));
                    ((CreatePlaylist) c).run(p);
                    break;
                    case "addRemoveInPlaylist":
                    c = new AddRemoveInPlaylist();
                    ((AddRemoveInPlaylist) c).setAddRemoveInPlaylist(commands.get(i));
                    ((AddRemoveInPlaylist) c).run(p);
                    break;
                    case "like":
                    c = new Like();
                    ((Like) c).setLike(commands.get(i));
                    ((Like) c).run(p);
                    break;
                    case "showPlaylists":
                    c = new ShowPlaylists();
                    ((ShowPlaylists) c).setShowPlaylists(commands.get(i));
                    ((ShowPlaylists) c).run(p);
                    break;
                case "showPreferredSongs":
                    c = new ShowPreferredSongs();
                    ((ShowPreferredSongs) c).setShowPreferredSongs(commands.get(i));
                    ((ShowPreferredSongs) c).run(p);
                    break;

            }
        }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        objectWriter.writeValue(new File(filePathOutput), output);
    }
}
