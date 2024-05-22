package com.haroldstudios.infoheads.serializer;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.serializer.typeadapter.AbstractTypeAdapter;
import com.haroldstudios.infoheads.serializer.typeadapter.EnumTypeAdapter;
import com.haroldstudios.infoheads.serializer.typeadapter.LocationTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileUtil {

    private final Gson gson = buildGson().create();

    private static final Map<String, Lock> locks = new HashMap<>();

    private GsonBuilder buildGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY)
                .registerTypeAdapter(Element.class, new AbstractTypeAdapter<Element>());
    }

    public static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    public static String getName(Object o) {
        return getName(o.getClass());
    }

    public File getFile(String name) {
        return new File(InfoHeads.getInstance().getDataFolder(), name + ".json");
    }

    public File getFile(Class<?> clazz) {
        return getFile(getName(clazz));
    }

    public File getFile(Object obj) {
        return getFile(getName(obj));
    }

    public <T> T load(Class<T> clazz) {
        return load(clazz, getFile(clazz));
    }

    public <T> T load(Class<T> clazz, File file) {
        String content = read(file);
        if (content == null) {
            return null;
        }

        try {
            return gson.fromJson(content, clazz);
        } catch (Exception ex) {
            InfoHeads.getInstance().error("Failed to parse " + file.toString() + ": " + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(InfoHeads.getInstance());
        }

        return null;
    }

    public void save(Object instance) {
        save(instance, getFile(instance));
    }


    public void save(Object instance, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                InfoHeads.getInstance().error(e.getMessage());
            }
        }
        write(file, gson.toJson(instance));
    }

    public static void write(final File file, final String content) {
        String name = file.getName();
        final Lock lock;

        // Create lock for each file if there isn't already one.
        if (locks.containsKey(name)) {
            lock = locks.get(name);
        } else {
            ReadWriteLock rwl = new ReentrantReadWriteLock();
            lock = rwl.writeLock();
            locks.put(name, lock);
        }
        lock.lock();
        try {
            file.createNewFile();
            Files.write(content, file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            InfoHeads.getInstance().error(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public static String read(File file) {
        try {
            int length = (int) file.length();
            byte[] output = new byte[length];
            InputStream in = new FileInputStream(file);
            int offset = 0;
            while (offset < length) {
                offset += in.read(output, offset, (length - offset));
            }
            in.close();

            return utf8(output);
        } catch (IOException e) {
            return null;
        }
    }

    public static String utf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
