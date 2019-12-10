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

    private Material material = MaterialUtils.PLAYER_HEAD.getMaterial();

    private List<ItemStack> generateSkulls() {

        List<ItemStack> list = Lists.newArrayList();

        for (String each : InfoHeads.getInstance().getConfig().getStringList("Heads")) {
            if(each.toLowerCase().startsWith("id:")){
                // Skip this item if HDB isn't loaded.
                if(InfoHeads.getInstance().getHdbApiModule() == null || InfoHeads.getInstance().getHdbApiModule().getHdbApi() == null) continue;
                
                ItemStack head;
                try{
                    head = InfoHeads.getInstance().getHdbApiModule().getHdbApi().getItemHead(each.toLowerCase().replace("id:", ""));
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

    void giveItems(Player player) {

        List<ItemStack> skulls = generateSkulls();
        for (ItemStack each : skulls) addItem(player, each);

    }

    private void addItem(Player player, ItemStack itemstack) {
        player.getInventory().addItem(itemstack);
    }
}
