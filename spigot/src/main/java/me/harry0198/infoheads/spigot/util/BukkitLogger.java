package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLogger implements Logger {

    private final JavaPlugin jp;
    private Level level;

    public BukkitLogger(JavaPlugin jp, Level level) {
        this.jp = jp;
        this.level = level;
    }

    @Override
    public void info(String msg) {
        jp.getLogger().info(msg);
    }

    @Override
    public void warn(String msg) {
        jp.getLogger().warning(msg);
    }

    @Override
    public void debug(String msg) {
        jp.getLogger().info(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        if (level == Level.DEBUG || level == Level.TRACE) {
            jp.getLogger().info(msg);
            jp.getLogger().throwing("", "", e);
        }
    }

    @Override
    public void trace(String msg) {
        if (level == Level.TRACE) {
            jp.getLogger().info(msg);
        }
    }

    @Override
    public void trace(String msg, Throwable e) {
        if (level == Level.TRACE) {
            jp.getLogger().info(msg);
            jp.getLogger().throwing("", "", e);
        }
    }

    @Override
    public void severe(String msg) {
        jp.getLogger().severe(msg);
    }

    @Override
    public void setLevel(Level level) {
        assert level != null;

        this.level = level;
    }
}
