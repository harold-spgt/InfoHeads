package me.harry0198.infoheads.core.config;

import me.harry0198.infoheads.core.utils.logging.Level;

public final class Configuration {
    private String languageTag;
    private boolean checkForUpdate;
    private Level loggingLevel;
    private int configVer;


    public Configuration() {}

    public Configuration(String languageTag, boolean checkForUpdate, Level loggingLevel, int configVer) {
        this.languageTag = languageTag;
        this.loggingLevel = loggingLevel;
        this.checkForUpdate = checkForUpdate;
        this.configVer = configVer;
    }

    public int getConfigVer() {
        return configVer;
    }

    public Level getLoggingLevel() {
        return loggingLevel;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public boolean isCheckForUpdate() {
        return checkForUpdate;
    }

    public void setLoggingLevel(Level loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    public void setConfigVer(int configVer) {
        this.configVer = configVer;
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }

    public void setCheckForUpdate(boolean checkForUpdate) {
        this.checkForUpdate = checkForUpdate;
    }
}
