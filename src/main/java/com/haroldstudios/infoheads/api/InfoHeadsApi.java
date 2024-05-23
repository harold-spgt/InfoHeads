package com.haroldstudios.infoheads.api;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public interface InfoHeadsApi {
    InfoHeadConfiguration createHead(Location location);
    @Nullable InfoHeadConfiguration getHead(Location location);
    void removeHead(Location location);
}
