package me.harry0198.infoheads.core.utils.logging;

public class DefaultLogger implements Logger {
    @Override
    public void info(String msg) {
        perform();
    }

    @Override
    public void warn(String msg) {
        perform();
    }

    @Override
    public void warn(String msg, Throwable e) {
        perform();
    }

    @Override
    public void debug(String msg) {
        perform();
    }

    @Override
    public void debug(String msg, Throwable e) {
        perform();
    }

    @Override
    public void trace(String msg) {
        perform();
    }

    @Override
    public void trace(String msg, Throwable e) {
        perform();
    }

    @Override
    public void severe(String msg) {
        perform();
    }

    @Override
    public void setLevel(Level level) {
        // Cannot set level where the logger does not exist.
    }

    @SuppressWarnings("all") // Must be sysout because no logger exists! and this should not be fatal.
    private void perform() {
        System.out.println("NO LOGGER INITIALIZED YET.");
    }
}
