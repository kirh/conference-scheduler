package by.epam.tc.conference.web.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Language {
    ENGLISH(new Locale("en")),
    RUSSIAN(new Locale("ru")),
    GERMAN(new Locale("de"));

    public static final Language DEFAULT = ENGLISH;

    private static final Map<String, Language> supportedLanguages = Collections.unmodifiableMap(createMap());

    private final Locale locale;

    Language(Locale locale) {
        this.locale = locale;
    }

    public String getCode() {
        return locale.getLanguage();
    }

    /**
     * Returns language display name.
     * @return capitalized language display name
     */
    public String getDisplayName() {
        return locale.getDisplayLanguage(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Indicates whether application has appropriate language for given language tag or not.
     * @param languageTag to lookup
     * @return true when contains appropriate language for given language tag
     */
    public static boolean contains(String languageTag) {
        return supportedLanguages.containsKey(languageTag);
    }

    /**
     * Returns appropriate language for given locale when found or default language otherwise.
     * @param locale to resolve
     * @return application locale
     */
    public static Language resolve(Locale locale) {
        String languageTag = locale.toLanguageTag();
        return resolve(languageTag);
    }

    /**
     * Returns appropriate language for given languageTag when found or default language otherwise.
     * @param languageTag to resolve
     * @return application locale
     */
    public static Language resolve(String languageTag) {
        Language language = supportedLanguages.get(languageTag);
        return language != null ? language : DEFAULT;
    }

    private static Map<String, Language> createMap() {
        final Map<String, Language> supportedLanguages = new HashMap<>();
        for (Language language : values()) {
            Locale locale = language.getLocale();
            String languageTag = locale.toLanguageTag();
            supportedLanguages.put(languageTag, language);
        }
        return supportedLanguages;
    }
}
