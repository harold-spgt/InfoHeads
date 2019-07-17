package me.harry0198.infoheads.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Builder
@Setter
public class LoadedLocations {

    private Location location;
    private List<String> command;
    private List<String> message;
}
