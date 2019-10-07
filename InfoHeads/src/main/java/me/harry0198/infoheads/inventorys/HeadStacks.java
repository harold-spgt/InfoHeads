package me.harry0198.infoheads.inventorys;

import com.google.common.collect.Lists;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.utils.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class HeadStacks {
    
    private InfoHeads plugin;
    
    public HeadStacks(InfoHeads plugin){
        this.plugin = plugin;
    }

    private Material material = MaterialUtils.PLAYER_HEAD.getMaterial();

    private List<ItemStack> generateSkulls() {

        List<ItemStack> list = Lists.newArrayList();

        for (String each : plugin.getConfig().getStringList("Heads")) {
            ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
            SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
            sm.setOwner(each);
            playerSkull.setItemMeta(sm);

            list.add(playerSkull);
        }

        return list;
    }

    void giveItems(Player player) {

        List<ItemStack> skulls = generateSkulls();
        for (ItemStack each : skulls) addItem(player, each);

    }

    private void addItem(Player player, ItemStack itemstack) {
        player.getInventory().addItem(itemstack);
    }
}
