package com.haroldstudios.infoheads.listeners;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HeadBreak implements Listener {
    private final InfoHeads plugin;

    public HeadBreak(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void headBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission(Constants.ADMIN_PERM)) return;
        if (!e.getPlayer().isSneaking()) return;

        Location loc = e.getBlock().getLocation();
        if (!plugin.getDataStore().getInfoHeads().containsKey(loc)) {
            return;
        }

        plugin.getDataStore().removeInfoHeadAt(loc);
        MessageUtil.sendMessage(e.getPlayer(), MessageUtil.Message.INFOHEAD_REMOVED);
    }

}
