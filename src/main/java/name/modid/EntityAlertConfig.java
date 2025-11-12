package com.example.entityalert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EntityAlertConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/entityalert.json");
    public static ConfigData DATA = new ConfigData();

    public static class ConfigData {
        public boolean showAlert = true;
        public double alertRange = 16.0;
    }

    public static void load() {
        try {
            if (!CONFIG_FILE.exists()) {
                save();
                return;
            }

            FileReader reader = new FileReader(CONFIG_FILE);
            DATA = GSON.fromJson(reader, ConfigData.class);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            if (!CONFIG_FILE.getParentFile().exists()) {
                CONFIG_FILE.getParentFile().mkdirs();
            }

            FileWriter writer = new FileWriter(CONFIG_FILE);
            GSON.toJson(DATA, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}