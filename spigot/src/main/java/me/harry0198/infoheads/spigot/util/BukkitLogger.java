package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.spigot.EntryPoint;

public class BukkitLogger implements Logger {

    private Level level;

    public BukkitLogger(Level level) {
        this.level = level;
    }

    @Override
    public void info(String msg) {
        EntryPoint.getInstance().getLogger().info(msg);
    }

    @Override
    public void warn(String msg) {
        EntryPoint.getInstance().getLogger().warning(msg);
    }

    @Override
    public void debug(String msg) {
        EntryPoint.getInstance().getLogger().info(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        if (level == Level.DEBUG || level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
            EntryPoint.getInstance().getLogger().throwing("", "", e);
        }
    }

    @Override
    public void trace(String msg) {
        if (level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
        }
    }

    @Override
    public void trace(String msg, Throwable e) {
        if (level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
            EntryPoint.getInstance().getLogger().throwing("", "", e);
        }
    }

    @Override
    public void severe(String msg) {
        EntryPoint.getInstance().getLogger().severe(msg);
    }

    @Override
    public void setLevel(Level level) {
        assert level != null;

        this.level = level;
    }
}
