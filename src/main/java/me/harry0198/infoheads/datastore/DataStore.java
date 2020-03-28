package me.harry0198.infoheads.datastore;

import me.harry0198.infoheads.InfoHeadConfiguration;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.elements.ConsoleCommandElement;
import me.harry0198.infoheads.elements.Element;
import me.harry0198.infoheads.elements.MessageElement;
import me.harry0198.infoheads.elements.PlayerCommandElement;
import me.harry0198.infoheads.gui.AbstractGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public final class DataStore {

    private final InfoHeads plugin;

    public static List<Player> placerMode = new ArrayList<>();

    private final Map<Player, InfoHeadConfiguration.Draft> drafts = new HashMap<>();
    private Map<Location, InfoHeadConfiguration> infoHeads = new HashMap<>();
    private Map<Player, AbstractGui> openMenus = new HashMap<>();

    public DataStore(final InfoHeads plugin) {
        this.plugin = plugin;
        readConfig();
    }

    public Map<Location, InfoHeadConfiguration> getInfoHeads() {
        return infoHeads;
    }

    public InfoHeadConfiguration.Draft getDraft(final Player player) {
        return drafts.get(player);
    }

    public void addDraft(final Player player, final InfoHeadConfiguration.Draft draft) {
        drafts.put(player, draft);
    }

    public void addInfoHead(final InfoHeadConfiguration infoHead) {
        infoHeads.put(infoHead.getLocation(), infoHead);
        addToConfig(infoHead);
    }

    public void removeDraft(final Player player) {
        drafts.remove(player);
    }

    public void removeOpenMenu(final Player player) {
        openMenus.remove(player);
    }

    public void addOpenMenu(final Player player, final AbstractGui gui) {
        openMenus.put(player, gui);
    }

    public AbstractGui getOpenMenu(final Player player) {
        return openMenus.get(player);
    }

    private void addToConfig(final InfoHeadConfiguration infoHead) {
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("Infoheads");
        String uuid = infoHead.getKey();
        root.set(uuid + ".location.x", infoHead.getLocation().getBlockX());
        root.set(uuid + ".location.y", infoHead.getLocation().getBlockY());
        root.set(uuid + ".location.z", infoHead.getLocation().getBlockZ());
        root.set(uuid + ".location.world", infoHead.getLocation().getWorld().getName());

        String elementRoot = uuid + ".elements";
        int i = 0;
        for (Element element : infoHead.getElementList()) {

            root.set(elementRoot + "." + i + ".content", element.getContent());
            root.set(elementRoot + "." + i + ".type", element.getType().toString());
            i++;
        }

        plugin.saveConfig();
    }

    private void readConfig() {

        ConfigurationSection root = plugin.getConfig().getConfigurationSection("Infoheads");

        Map<Location, InfoHeadConfiguration> infoHeads = new HashMap<>();

        for (String key : root.getKeys(false)) {

            ConfigurationSection locRoot = root.getConfigurationSection(key + ".location");
            if (locRoot == null) continue;

            Location location = new Location(Bukkit.getWorld(locRoot.getString("world")), locRoot.getLong("x"), locRoot.getLong("y"), locRoot.getLong("z"));
            String permission = root.getString(key + "permission");

            ConfigurationSection elementRoot = root.getConfigurationSection(key + ".elements");
            List<Element> elementList = new ArrayList<>();
            if (elementRoot != null)
                for (String k : elementRoot.getKeys(false)) {
                    Element.InfoHeadType type;
                    try {
                        type = Element.InfoHeadType.valueOf(elementRoot.getString(k + ".type"));
                    } catch (IllegalArgumentException args) {
                        plugin.warn("Invalid Type of value " + k);
                        continue;
                    }
                    switch (type) {
                        case MESSAGE:
                            elementList.add(new MessageElement(elementRoot.getString(k + ".content")));
                            break;
                        case CONSOLE_COMMAND:
                            elementList.add(new ConsoleCommandElement(elementRoot.getString(k + ".content")));
                            break;
                        case PLAYER_COMMAND:
                            elementList.add(new PlayerCommandElement(elementRoot.getString(k + ".content")));
                            break;
                    }
                }

            infoHeads.put(location, new InfoHeadConfiguration(location, elementList, permission));
        }

        this.infoHeads = infoHeads;
    }
}
