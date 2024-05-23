package com.haroldstudios.infoheads.ui;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.hooks.BlockParticlesHook;
import com.haroldstudios.infoheads.utils.MessageUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.badbones69.blockparticles.api.enums.BPParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class ParticleSelectorGui extends AbstractGui {

    public ParticleSelectorGui(Player player, InfoHeads plugin, InfoHeadConfiguration configuration) {
        super(player, plugin, 6, MessageUtil.getString(MessageUtil.Message.PARTICLES_GUI_TITLE), configuration);

        getGui().setDefaultClickAction(event -> event.setCancelled(true));
        getGui().getFiller().fill(new GuiItem(new ItemStack(Objects.requireNonNull(Material.BLACK_STAINED_GLASS_PANE))));

        final Random random = new Random();
        int slot = 0;

        for (BPParticles each : BPParticles.values()) {
            // If is about to go into overflow, cancel the operation.
            if (slot > 45) break;

            getGui().setItem(slot, new GuiItem(
                    ItemBuilder.from(getRandomMaterial(random))
                            .name(MessageUtil.toComponent(ChatColor.BLUE + capitalize(each.toString().toLowerCase())))
                            .glow(true)
                            .lore(MessageUtil.getComponentList(MessageUtil.Message.PARTICLE_GUI_LORE))
                            .build(), event -> {
                        if (event.isLeftClick()) {
                            if (getPlugin().blockParticles) {
                                BlockParticlesHook.sendParticle((Player) event.getWhoClicked(), configuration.getId().toString(), each.toString());
                                configuration.setParticle(each.toString());
                            }
                        } else {
                            BlockParticlesHook.removeParticle(event.getWhoClicked(), configuration.getId().toString());
                            configuration.setParticle(null);
                        }
            }));
            slot++;
        }

        getGui().setItem(49, new GuiItem(ItemBuilder.from(Material.BARRIER).name(MessageUtil.getComponent(MessageUtil.Message.BACK)).glow(true).build(), event -> new WizardGui(plugin, player, configuration).open()));
    }

    public Material getRandomMaterial(final Random random) {

        Material material = Material.values()[random.nextInt(Material.values().length)];
        return Bukkit.getServer().getRecipesFor(new ItemStack(material)).isEmpty() ? getRandomMaterial(random) : material;

    }

    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
