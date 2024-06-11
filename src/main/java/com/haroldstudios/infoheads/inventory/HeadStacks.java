package com.haroldstudios.infoheads.inventory;

import com.google.common.collect.Lists;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.ui.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class HeadStacks {

    private List<ItemStack> generateSkulls() {

        List<ItemStack> list = Lists.newArrayList();

        for (String each : InfoHeads.getInstance().getConfig().getStringList("Heads")) {
            //TODO - Fake player profiles
            ItemStack playerSkull = new ItemBuilder(Material.PLAYER_HEAD)
//                    .owner(Bukkit.getOfflinePlayer(each))
                    .build();

            list.add(playerSkull);
        }

        return list;
    }

    public static void giveHeads(Player player) {
        CompletableFuture.runAsync(() -> new HeadStacks().giveItems(player));
//        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new HeadStacks().giveItems(player));
    }

    void giveItems(Player player) {

        List<ItemStack> skulls = generateSkulls();
        for (ItemStack each : skulls) addItem(player, each);

    }

    private void addItem(Player player, ItemStack itemstack) {
        player.getInventory().addItem(itemstack);
    }
}
