package com.haroldstudios.infoheads.datastore;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.components.hooks.BlockParticlesHook;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public final class DataStore {

    public transient static Map<Player, InfoHeadConfiguration> placerMode = new HashMap<>();

    @Getter private final Map<Location, InfoHeadConfiguration> infoHeads = new HashMap<>();

    public void addInfoHead(final InfoHeadConfiguration infoHead) {
        infoHeads.put(infoHead.getLocation(), infoHead);
        InfoHeads.getInstance().getFileUtil().save(this);
    }

    public void forceSetInfoHeads(Map<Location, InfoHeadConfiguration> heads) {
        this.infoHeads.putAll(heads);
        InfoHeads.getInstance().getFileUtil().save(this);
    }

    public void removeInfoHeadAt(Location location) {
        BlockParticlesHook.removeParticle(InfoHeads.getInstance().getServer().getConsoleSender(), infoHeads.get(location).getId().toString());
        infoHeads.remove(location);
        InfoHeads.getInstance().getFileUtil().save(this);
    }

    public InfoHeadConfiguration getMatchingInfoHead(final InfoHeadConfiguration infoHeadConfiguration) {
        Optional<InfoHeadConfiguration> infoHead = infoHeads.values().stream().filter(each -> each.getId().toString().equals(infoHeadConfiguration.getId().toString())).findFirst();

        return infoHead.orElse(null);
    }

    public Location getKeyByValue(InfoHeadConfiguration value) {
        Optional<Location> loc = infoHeads.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst();

        return loc.orElse(null);

    }
}
