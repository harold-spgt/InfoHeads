package me.harry0198.infoheads.core.commands;


///**
// * The {@code ReloadCmdExecutor} class extends {@link CmdExecutor} to handle the
// * reloading of the plugin's configuration and data. It ensures that all necessary
// * components are properly saved and reloaded.
// */
//public class ReloadCmdExecutor extends CmdExecutor {
//
//    private final InfoHeads plugin;
//    private final FileUtil fileUtil;
//    private final DataStore dataStore;
//
//    /**
//     * Constructs a new {@code ReloadCmdExecutor} with the specified {@link InfoHeads} plugin,
//     * {@code FileUtil}, and {@link DataStore}.
//     *
//     * @param plugin the {@link InfoHeads} plugin instance
//     * @param fileUtil the {@link FileUtil} instance used for saving data
//     * @param dataStore the {@link DataStore} instance used to manage InfoHeads data
//     */
//    public ReloadCmdExecutor(InfoHeads plugin, FileUtil fileUtil, DataStore dataStore) {
//        super(Constants.ADMIN_PERM);
//        this.plugin = plugin;
//        this.fileUtil = fileUtil;
//        this.dataStore = dataStore;
//    }
//
//    /**
//     * Executes the reload command for the given {@link CommandSender}. If the sender is
//     * a player, it saves the current data, unregisters relevant event handlers, reloads
//     * the plugin, and sends a confirmation message to the player.
//     *
//     * @param sender the {@link CommandSender} executing the command, expected to be a {@code Player}
//     * @return {@code true} if the command was successfully executed, otherwise {@code false}
//     */
//    @Override
//    public boolean executeCmd(CommandSender sender) {
//        fileUtil.save(dataStore);
//        BlockPlaceEvent.getHandlerList().unregister(plugin);
//        PlayerInteractEvent.getHandlerList().unregister(plugin);
//        plugin.load();
//        sender.sendMessage(MessageUtil.getString(MessageUtil.Message.RELOAD));
//        return true;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean isPlayerOnlyCmd() {
//        return false;
//    }
//}
