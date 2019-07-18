package me.harry0198.infoheads.inventorys;

import me.harry0198.infoheads.utils.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadStacks {

    private Material material = MaterialUtils.PLAYER_HEAD.getMaterial();

    private ItemStack questionSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Question");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack exclamationSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Exclamation");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack chestSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Chest");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack faceBookSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Facebook");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack arrowUpSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowUp");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack arrowDownSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowDown");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack arrowLeftSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowLeft");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    private ItemStack arrowRightSkull() {

        ItemStack playerSkull = new ItemStack(material, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowRight");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    void giveItems(Player player) {
        addItem(player, questionSkull());
        addItem(player, exclamationSkull());
        addItem(player, arrowUpSkull());
        addItem(player, arrowDownSkull());
        addItem(player, arrowLeftSkull());
        addItem(player, arrowRightSkull());
        addItem(player, faceBookSkull());
        addItem(player, chestSkull());
    }

    private void addItem(Player player, ItemStack itemstack) {
        player.getInventory().addItem(itemstack);
    }
}
