package com.haroldstudios.infoheads.components.hooks;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class HdbListener implements Listener {

    private final InfoHeads plugin;

    public HdbListener(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent event) {
        // Load a new instance of the Head Database if this event fires.
        plugin.hdb = new HdbHook();
        plugin.hdb.setHdbApi(new HeadDatabaseAPI());
    }
}
