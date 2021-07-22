package com.haroldstudios.infoheads.tasks;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.MessageUtil;

import java.io.IOException;
import java.util.logging.Level;

public class ConfigTask implements Runnable {
    private final InfoHeads plugin;

    public ConfigTask(InfoHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!MessageUtil.isNeedSave()) {
            return;
        }

        try {
            plugin.getMessagesConfig().save(plugin.getMessagesFile());
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "An error has occurred while trying to auto-update the messages.yml file..", e);
        }
    }
}
