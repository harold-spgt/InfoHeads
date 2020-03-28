package me.harry0198.infoheads.conversations;

import me.harry0198.infoheads.InfoHeads;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.entity.Player;

public final class ConversationAbandoned implements ConversationAbandonedListener {

    private final InfoHeads plugin;

    public ConversationAbandoned(InfoHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        plugin.getDataStore().getOpenMenu((Player) abandonedEvent.getContext().getForWhom()).open();
    }
}
