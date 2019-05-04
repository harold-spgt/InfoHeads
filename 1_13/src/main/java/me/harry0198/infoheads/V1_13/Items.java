package me.harry0198.infoheads.V1_13;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

    Material material = Material.PLAYER_HEAD;

    /**
     * Gives the Question Mark ItemStack
     *
     * @return itemstack PlayerSkull
     */
    @SuppressWarnings("deprecation")
    public ItemStack questionSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Question");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack exclamationSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Exclamation");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack chestSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Chest");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack faceBookSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_Facebook");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack arrowUpSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowUp");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack arrowDownSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowDown");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack arrowLeftSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowLeft");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

    @SuppressWarnings("deprecation")
    public ItemStack arrowRightSkull() {

        ItemStack playerSkull = new ItemStack(material);
        SkullMeta sm = (SkullMeta) playerSkull.getItemMeta();
        sm.setOwner("MHF_ArrowRight");
        playerSkull.setItemMeta(sm);

        return playerSkull;
    }

}
