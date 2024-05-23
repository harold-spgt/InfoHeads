package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCmdExecutor extends CmdExecutor {

    private final DataStore dataStore;
    public CreateCmdExecutor(DataStore dataStore) {
        super(Constants.ADMIN_PERM);
        this.dataStore = dataStore;
    }

    @Override
    public boolean executeCmd(CommandSender sender) {
        Player player = (Player) sender;

        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();
        if (dataStore.getInfoHeads().containsKey(targetLoc)) {
            MessageUtil.sendMessage(sender, MessageUtil.Message.NO_INFOHEAD_AT_LOC);
            return true;
        }

        InfoHeadConfiguration configuration = new InfoHeadConfiguration();
//        configuration.setLocation(targetLoc);
//        dataStore.addInfoHead(configuration);
//
//        if (plugin.blockParticles && configuration.getParticle() != null) {
//            BlockParticlesHook.newLoc(player, configuration.getId().toString(), configuration.getParticle());
//        }
//
//        new WizardGui(plugin, player, configuration).open();
        return true;
    }

    @Override
    public boolean isPlayerOnlyCmd() {
        return false;
    }
}
