package me.harry0198.infoheads.utils;

import org.bukkit.Location;

import java.util.List;

public class LoadedLocations {

    private Location location;
    private List<String> commands;
    private List<String> messages;
    private String key;

    private LoadedLocations(Location location, List<String> commands, List<String> messages, String key) {
        this.location = location;
        this.commands = commands;
        this.messages = messages;
        this.key = key;
    }

    public Location getLocation() { return this.location; }
    public List<String> getCommands() { return this.commands; }
    public List<String> getMessages() { return this.messages; }
    public String getKey() { return this.key; }

    public static class Builder {

        private Location location;
        private List<String> command;
        private List<String> message;
        private String key;

        public Builder() {
        }

        public Builder setCommand(List<String> command) {
            this.command = command;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setMessage(List<String> message) {
            this.message = message;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public LoadedLocations build() {
            return new LoadedLocations(location, command, message, key);
        }
    }
}
