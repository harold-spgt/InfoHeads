package me.harry0198.infoheads.spigot.ui.particles;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.hooks.BlockParticlesHook;
import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import me.harry0198.infoheads.spigot.ui.wizard.WizardGui;
import me.harry0198.infoheads.core.ui.WizardViewModel;
import com.haroldstudios.infoheads.utils.MessageUtil;
import me.badbones69.blockparticles.api.enums.BPParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ParticleSelectorGui extends InventoryGui {
    public ParticleSelectorGui(InfoHeads plugin, InfoHeadConfiguration configuration) {
        super(6, MessageUtil.getString(MessageUtil.Message.PARTICLES_GUI_TITLE));

        setDefaultClickAction(event -> event.setCancelled(true));
        fillEmpty(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), null));

        final Random random = new Random();
        int slot = 0;

        for (BPParticles each : BPParticles.values()) {
            // If is about to go into overflow, cancel the operation.
            if (slot > 45) break;

            insert(slot, new GuiItem(
                    new ItemBuilder(getRandomMaterial(random))
                            .name(ChatColor.BLUE + capitalize(each.toString().toLowerCase()))
                            .glow(true)
                            .lore(MessageUtil.getStringList(MessageUtil.Message.PARTICLE_GUI_LORE))
                            .build(), event -> {
                if (event.isLeftClick()) {
                    if (plugin.blockParticles) {
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

        // Insert back button.
        insert(49, new GuiItem(
                new ItemBuilder(Material.BARRIER)
                        .name(MessageUtil.getString(MessageUtil.Message.BACK))
                        .glow(true).build(),
                event -> new WizardGui(new WizardViewModel(plugin, configuration)).open(event.getWhoClicked())));
    }

    public Material getRandomMaterial(final Random random) {

        Material material = Material.values()[random.nextInt(Material.values().length)];
        return Bukkit.getServer().getRecipesFor(new ItemStack(material)).isEmpty() ? getRandomMaterial(random) : material;

    }

    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}
