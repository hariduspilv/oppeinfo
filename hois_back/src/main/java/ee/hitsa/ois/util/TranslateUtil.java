package ee.hitsa.ois.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.exception.BadConfigurationExcecption;

public class TranslateUtil {

    /**
     * Get name of object in given language
     *
     * @param object
     * @param lang
     * @return
     * @throws NullPointerException if lang is null
     * @throws IllegalArgumentException if language is not supported
     */
    public static String name(Translatable object, Language lang) {
        if(object == null) {
            return null;
        }

        switch(Objects.requireNonNull(lang)) {
        case ET:
            return object.getNameEt();
        case EN:
            return object.getNameEn();
        case RU:
            return object.getNameRu();
        default:
            throw new IllegalArgumentException("Unsupported language");
        }
    }

    /**
     * Translates string
     * @param key
     * @param lang
     * @return
     * @throws NullPointerException if key or lang are null
     */
    public static String translate(String key, Language lang) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(lang);

        ResourceBundle bundle = BUNDLE_CACHE.computeIfAbsent(lang, l -> loadBundle(l));
        try {
            return bundle.getString(key);
        } catch(@SuppressWarnings("unused") MissingResourceException e) {
            return "? - " + key;
        }
    }

    private static ResourceBundle loadBundle(Language lang) {
        String bundleName = "/i18n/"+lang.name().toLowerCase()+".properties";
        try(InputStream is = TranslateUtil.class.getResourceAsStream(bundleName)) {
            if(is == null) {
                throw new IllegalArgumentException("Bundle " + bundleName + " not found");
            }
            return new PropertyResourceBundle(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new BadConfigurationExcecption(String.format("Bundle \"%s\" not found", bundleName), e);
        }
    }

    private static final ConcurrentMap<Language, ResourceBundle> BUNDLE_CACHE = new ConcurrentHashMap<>();
}
