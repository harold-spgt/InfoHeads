package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.spigot.EntryPoint;

import java.util.Objects;

public class BukkitLogger implements Logger {

    private Level level;

    public BukkitLogger(Level level) {
        this.level = level;
    }

    @Override
    public void info(String msg) {
        Objects.requireNonNull(msg);

        EntryPoint.getInstance().getLogger().info(msg);
    }

    @Override
    public void warn(String msg) {
        Objects.requireNonNull(msg);

        EntryPoint.getInstance().getLogger().warning(msg);
    }

    @Override
    public void warn(String msg, Throwable e) {
        Objects.requireNonNull(msg);
        Objects.requireNonNull(e);

        EntryPoint.getInstance().getLogger().warning(msg);
        EntryPoint.getInstance().getLogger().throwing("", "", e);
    }

    @Override
    public void debug(String msg) {
        Objects.requireNonNull(msg);

        if (level == Level.DEBUG || level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
        }
    }

    @Override
    public void debug(String msg, Throwable e) {
        Objects.requireNonNull(msg);
        Objects.requireNonNull(e);

        if (level == Level.DEBUG || level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
            EntryPoint.getInstance().getLogger().throwing("", "", e);
        }
    }

    @Override
    public void trace(String msg) {
        Objects.requireNonNull(msg);

        if (level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
        }
    }

    @Override
    public void trace(String msg, Throwable e) {
        Objects.requireNonNull(msg);
        Objects.requireNonNull(e);

        if (level == Level.TRACE) {
            EntryPoint.getInstance().getLogger().info(msg);
            EntryPoint.getInstance().getLogger().throwing("", "", e);
        }
    }

    @Override
    public void severe(String msg) {
        Objects.requireNonNull(msg);

        EntryPoint.getInstance().getLogger().severe(msg);
    }

    @Override
    public void setLevel(Level level) {
        Objects.requireNonNull(level);

        this.level = level;
    }
}
