package com.leclowndu93150.keymap.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leclowndu93150.keymap.Keymap;

import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Credits {
    private static final String CREDITS_ROOT = "assets/keymap/credits.json";
    static Credits instance;

    List<LanguageCredits> language;
    List<LayoutCredits> layout;
    List<CoreCredits> core;

    public List<LanguageCredits> language() { return language; }
    public List<LayoutCredits> layout() { return layout; }
    public List<CoreCredits> core() { return core; }

    public static class LanguageCredits {
        String lang;
        List<String> name;

        public String lang() { return lang; }
        public List<String> name() { return name; }
    }

    public static class LayoutCredits {
        String key;
        List<String> name;

        public String key() { return key; }
        public List<String> name() { return name; }
    }

    public static class CoreCredits {
        String name;
        List<String> contributions;

        public String name() { return name; }
        public List<String> contributions() { return contributions; }
    }

    public static synchronized Credits instance() {
        if (instance == null) {
            loadCredits();
        }
        return instance;
    }

    public static void loadCredits() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        ClassLoader loader = Credits.class.getClassLoader();

        try {
            URI creditUri =
                    Objects.requireNonNull(loader.getResource(CREDITS_ROOT)).toURI();

            if (creditUri.getScheme().equals("jar")) {
                try {
                    FileSystems.getFileSystem(creditUri);
                } catch (Exception e) {
                    FileSystems.newFileSystem(creditUri, Collections.emptyMap());
                }
            }

            try (InputStreamReader reader = new InputStreamReader(
                    Objects.requireNonNull(loader.getResourceAsStream(CREDITS_ROOT)), StandardCharsets.UTF_8)) {
                instance = gson.fromJson(reader, Credits.class);
            }

        } catch (Exception e) {
            Keymap.logger().warn("CANT LOAD CREDITS", e);
        }
    }
}
