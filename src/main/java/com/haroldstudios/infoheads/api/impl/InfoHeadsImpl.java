package com.haroldstudios.infoheads.api.impl;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.api.InfoHeadsApi;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class InfoHeadsImpl implements InfoHeadsApi {

    @Override
    public InfoHeadConfiguration createHead(Location location) {
        InfoHeadConfiguration configuration = new InfoHeadConfiguration();
        configuration.setLocation(location);
        InfoHeadConfiguration matched = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);

        if (matched == null) {
            InfoHeads.getInstance().getDataStore().addInfoHead(configuration);
            return configuration;
        }

        return matched;
    }

    @Nullable
    @Override
    public InfoHeadConfiguration getHead(Location location) {
        return InfoHeads.getInstance().getDataStore().getInfoHeads().get(location);
    }

    @Override
    public void removeHead(Location location) {
        InfoHeads.getInstance().getDataStore().removeInfoHeadAt(location);
    }

}
