package me.harry0198.infoheads.spigot.ui.builder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * {@link ItemStack} utility to allow quick and easy item building in a
 * chain.
 */
public class ItemBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;


    public ItemBuilder(Material material) {
        if (material == null) throw new IllegalArgumentException("Material cannot be null");
        this.itemStack = new ItemStack(material);

        // Get ItemMeta or assign it one.
        this.itemMeta = itemStack.hasItemMeta() ?
                itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The String name to give.
     * @return {@link ItemBuilder}
     */
    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore Lore lines as args
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(String... lore) {
        return lore(Arrays.stream(lore).toList());
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore {@link List} with the lore lines
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    /**
     * Adds or removes the {@link ItemStack} glow
     * @param glow Should the item glow.
     * @return {@link ItemBuilder}
     */
    public ItemBuilder glow(boolean glow) {
        if (glow) {
            // Add enchant and hide it.
            itemMeta.addEnchant(Enchantment.LURE, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            // If false, remove all enchantments to remove glow.
            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                itemMeta.removeEnchant(enchantment);
            }
        }

        return this;
    }

    /**
     * Sets the skull texture using a BASE64 string
     *
     * @param texture The base64 texture
     * @return {@link ItemBuilder}
     */
    @NotNull
    public ItemBuilder texture(@NotNull final String texture) {
        if (itemStack.getType() != Material.PLAYER_HEAD) return this;

        final String textureUrl = getSkinUrl(texture);

        if (textureUrl == null) {
            return this;
        }

        UUID uid = UUID.randomUUID();
        final SkullMeta skullMeta = (SkullMeta) itemMeta;
        final PlayerProfile profile = Bukkit.createPlayerProfile(uid, "");
        final PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(new URL(textureUrl));
        } catch (MalformedURLException e) {
            LOGGER.debug("Failed to texture", e);
            return this;
        }

        profile.setTextures(textures);
        skullMeta.setOwnerProfile(profile);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Build the {@link ItemStack} from the provided builder options.
     * @return {@link ItemStack} with options applied.
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Decode a base64 string and extract the url of the skin. Example:
     * <br>
     * - Base64: {@code eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNlYjE3MDhkNTQwNGVmMzI2MTAzZTdiNjA1NTljOTE3OGYzZGNlNzI5MDA3YWM5YTBiNDk4YmRlYmU0NjEwNyJ9fX0=}
     * <br>
     * - JSON: {@code {"textures":{"SKIN":{"url":"http://textures.minecraft.net/texture/dceb1708d5404ef326103e7b60559c9178f3dce729007ac9a0b498bdebe46107"}}}}
     * <br>
     * - Result: {@code http://textures.minecraft.net/texture/dceb1708d5404ef326103e7b60559c9178f3dce729007ac9a0b498bdebe46107}
     * @param base64Texture the texture
     * @return the url of the texture if found, otherwise {@code null}
     */
    private static String getSkinUrl(String base64Texture) {
        final String decoded = new String(Base64.getDecoder().decode(base64Texture));
        final JsonObject object = new Gson().fromJson(decoded, JsonObject.class);

        final JsonElement textures = object.get("textures");

        if (textures == null) {
            return null;
        }

        final JsonElement skin = textures.getAsJsonObject().get("SKIN");

        if (skin == null) {
            return null;
        }

        final JsonElement url = skin.getAsJsonObject().get("url");
        return url == null ? null : url.getAsString();
    }
}
