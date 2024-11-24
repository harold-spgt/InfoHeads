package me.harry0198.infoheads.core.utils.logging;

public interface Logger {

    void info(String msg);
    void warn(String msg);
    void warn(String msg, Throwable e);
    void debug(String msg);
    void debug(String msg, Throwable e);
    void trace(String msg);
    void trace(String msg, Throwable e);
    void severe(String msg);
    void setLevel(Level level);
}
