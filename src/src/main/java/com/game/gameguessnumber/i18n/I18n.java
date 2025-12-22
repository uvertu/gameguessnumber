package com.game.gameguessnumber.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {
    private static final String BUNDLE_BASE = "com.game.gameguessnumber.i18n.messages";
    private static Locale locale = Locale.forLanguageTag("ru");

    private I18n() {
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale newLocale) {
        if (newLocale == null) {
            return;
        }
        locale = newLocale;
    }

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BUNDLE_BASE, locale);
    }
}
