package com.haroldstudios.infoheads.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public final class HeadStacks {

    private final Material material = XMaterial.PLAYER_HEAD.parseMaterial();

    private List<ItemStack> generateSkulls() {

        List<ItemStack> list = Lists.newArrayList();

        /**
         * Checks if Head defined is from HeadDatabase
         * Special thanks to
         * @Contributor Andre_601
         */
        for (String each : InfoHeads.getInstance().getConfig().getStringList("Heads")) {
            if(each.toLowerCase().startsWith("id:")){
                // Skip this item if HDB isn't loaded.
                if(InfoHeads.getInstance().hdb == null || InfoHeads.getInstance().hdb.getHdbApi() == null) continue;

                ItemStack head;
                try{
                    head = InfoHeads.getInstance().hdb.getHdbApi().getItemHead(each.toLowerCase().replace("id:", ""));
                }catch(NullPointerException ignored){
                    head = null;
                }

                // Head/ID was invalid. Skipping to next entry.
                if(head == null) continue;

                // Add skull to list and move to next entry to prevent issues.
                list.add(head);
                continue;
            }

            ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
            SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
            sm.setOwner(each);
            playerSkull.setItemMeta(sm);

            list.add(playerSkull);
        }

        return list;
    }

    public static void giveHeads(InfoHeads plugin, Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new HeadStacks().giveItems(player));
    }

    void giveItems(Player player) {

        List<ItemStack> skulls = generateSkulls();
        for (ItemStack each : skulls) addItem(player, each);

    }

    private void addItem(Player player, ItemStack itemstack) {
        player.getInventory().addItem(itemstack);
    }
}
