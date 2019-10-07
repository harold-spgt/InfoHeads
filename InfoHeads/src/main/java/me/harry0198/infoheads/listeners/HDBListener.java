package me.harry0198.infoheads.listeners;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.harry0198.infoheads.InfoHeads;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HDBListener implements Listener{
    
    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent event){
        // Load a new instance of the Head Database if this event fires.
        InfoHeads.getInstance().setHdbApi(new HeadDatabaseAPI());
    }
}
