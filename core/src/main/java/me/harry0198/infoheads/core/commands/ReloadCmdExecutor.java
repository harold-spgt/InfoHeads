package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.repository.Repository;
//
///**
// * The {@code ReloadCmdExecutor} class extends {@link CmdExecutor} to handle the
// * reloading of the plugin's configuration and data. It ensures that all necessary
// * components are properly saved and reloaded.
// */
//public class ReloadCmdExecutor extends CmdExecutor {
//
//
//    public ReloadCmdExecutor(UserS) {
//        super(Constants.ADMIN_PERM);
//    }
//
//    /**
//     * Executes the reload command for the given {@link OnlinePlayer}. If the sender is
//     * a player, it saves the current data, unregisters relevant event handlers, reloads
//     * the plugin, and sends a confirmation message to the player.
//     *
//     * @param sender the {@link OnlinePlayer} executing the command.
//     * @return {@code true} if the command was successfully executed, otherwise {@code false}
//     */
//    @Override
//    public boolean executeCmd(OnlinePlayer sender) {
//        fileUtil.save(dataStore);
//        BlockPlaceEvent.getHandlerList().unregister(plugin);
//        PlayerInteractEvent.getHandlerList().unregister(plugin);
//        plugin.load();
//        sender.sendMessage(MessageUtil.getString(MessageUtil.Message.RELOAD));
//        return true;
//    }
//}
