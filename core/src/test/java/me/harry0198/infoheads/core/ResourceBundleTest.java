package me.harry0198.infoheads.core;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceBundleTest {

    @Test
    public void whenGetBundleExampleResourceForLocalePlPl_thenItShouldInheritPropertiesGreetingAndLanguage() {
        Locale plLocale = Locale.forLanguageTag("en_GB");

        LocalizedMessageService localizedMessageService = new LocalizedMessageService(plLocale);
    }
}
