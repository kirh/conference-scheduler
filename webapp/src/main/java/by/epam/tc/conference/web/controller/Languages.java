package by.epam.tc.conference.web.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Languages {
    ENGLISH("en", "English"),
    RUSSIAN("ru", "Русский"),
    GERMAN("de", "Deutch");

    private static final Set<String> languageCodes = Collections.unmodifiableSet(createSet());
    public static final Languages DEFAULT = ENGLISH;

    private final String code;
    private final String displayName;

    Languages(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static boolean contains(String languageCode) {
        return languageCodes.contains(languageCode);
    }

    private static Set<String> createSet() {
        final Set<String> languageCodes = new HashSet<>();
        for (Languages language : values()) {
            languageCodes.add(language.code);
        }
        return languageCodes;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
