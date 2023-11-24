package main;
/**
 * Class that contains the commands, used for parsing
 */
public class Command {
    private String command;

    private String username;

    private String timestamp;
    private int itemNumber;
    private Filters filters;
    private String type;

    private String playlistName;

    private int playlistId;

    private int seed;
    /**
     * Method that runs the  command(overloaded in the subclasses)
     */
    public void run() {
    }

    /**
     * Method that returns the filters
     */
    public Filters getFilters() {
        return filters;
    }
    /**
     * Method that sets the filters
     */
    public void setFilters(final Filters filters) {
        this.filters = filters;
    }
    /**
     * Method that returns the command
     */
    public String getCommand() {
        return command;
    }
    /**
     * Method that sets the command
     */
    public void setCommand(final String command) {
        this.command = command;
    }
    /**
     * Method that returns the type
     */
    public String getType() {
        return type;
    }
    /**
     * Method that sets the type
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     * Method that returns the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Method that sets the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }
    /**
     * Method that returns the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }
    /**
     * Method that sets the timestamp
     */
    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * Method that returns the item number
     */
    public int getItemNumber() {
        return itemNumber;
    }
    /**
     * Method that sets the item number
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }
    /**
     * Method that returns the playlist name
     */
    public String getPlaylistName() {
        return playlistName;
    }
    /**
     * Method that sets the playlist name
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }
    /**
     * Method that returns the playlist id
     */
    public int getPlaylistId() {
        return playlistId;
    }
    /**
     * Method that sets the playlist id
     */
    public void setPlaylistId(final int playlistIl) {
        this.playlistId = playlistIl;
    }
    /**
     * Method that returns the seed
     */
    public int getSeed() {
        return seed;
    }
    /**
     * Method that sets the seed
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }
}
