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

    }

    private void perform() {
        System.out.println("NO LOGGER INITIALIZED YET.");
    }
}
