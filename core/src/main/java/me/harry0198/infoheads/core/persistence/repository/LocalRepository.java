package me.harry0198.infoheads.core.persistence.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.persistence.entity.Identifiable;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * On-disk repository for saving, fetching and deleting {@link T} data.
 * @param <T> Class extending {@link Serializable} and {@link Identifiable}.
 */
public class LocalRepository<T extends Serializable & Identifiable> implements Repository<T> {

    private static final String DATA_FILE_EXTENSION = ".dat";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Path repositoryFolder;
    private final Type type;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
            .registerTypeAdapter(Element.class, new AbstractTypeAdapter<>())
            .create();

    /**
     * Class constructor.
     * @param parent Path to directory to save data to.
     */
    @Inject
    public LocalRepository(Path parent, Type type) {
        this.repositoryFolder = Path.of(parent.toFile().getAbsolutePath());
        this.type = type;
    }

    /**
     * Saves an object to its own file on the disk under the name defined by {@link Identifiable#getId()}.
     * @param obj Object implementing serializable and identifiable to save.
     * @return If the save was a success or not.
     */
    @Override
    public boolean save(T obj) {
        if (!validateFolder(repositoryFolder.toFile())) return false;

        File propertyFile = getFileForProperty(obj);

        try (FileWriter fileWriter = new FileWriter(propertyFile)) {
            gson.toJson(obj, fileWriter);
        } catch (IOException e) {
            LOGGER.warn("Unable to locally save obj " + obj.getId());
            LOGGER.debug("save", e);
            return false;
        }


        return true;
    }

    /**
     * Fetches all objects as List.
     * @return {@link List} of all successfully deserialized objects from the supplied repository directory via constructor.
     */
    @Override
    public List<T> getAll() {
        // If folder presence could not be verified, return empty list to prevent fatal failure and delegate notification to below function.
        File dataFolder = repositoryFolder.toFile();
        if (!validateFolder(dataFolder)) return List.of();

        File[] files = dataFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(DATA_FILE_EXTENSION));
        if (files == null) return List.of();

        
        return Arrays.stream(files).parallel().map(file -> {
            try (FileReader fileReader = new FileReader(file)) {
                JsonReader reader = new JsonReader(fileReader);
                return gson.<T>fromJson(reader, type);
            } catch (IOException i) {
                LOGGER.severe("Error fetching data from file " + file.getName());
                LOGGER.debug("getAll", i);
            }

            return null;
        }).filter(Objects::nonNull)
                .toList();
    }

    /**
     * Deletes data file from local disk. Uses {@link Identifiable#getId()} as file name.
     * @param obj to delete.
     * @return If the deletion was successful or not.
     */
    @Override
    public boolean delete(T obj) {
        File propertyFile = getFileForProperty(obj);
        try {
            Files.delete(propertyFile.toPath());
        } catch (IOException ex) {
            LOGGER.warn("Unable to locally delete from disk InfoHead " + obj.getId());
            LOGGER.debug("delete", ex);

            return false;
        }

        return true;
    }

    public static String getDataFileExtension() {
        return DATA_FILE_EXTENSION;
    }

    private boolean validateFolder(File dataFolder) {
        if (!dataFolder.isDirectory()) {
            try {
                boolean didMake = dataFolder.mkdirs();
                if (!didMake) {
                    LOGGER.severe("Failed to create missing directory " + dataFolder);
                }
                return didMake;
            } catch (SecurityException securityException) {
                LOGGER.severe("Failed to create missing directory - Missing WRITE permissions to directory " + dataFolder);
                LOGGER.debug("validateFolder", securityException);
                return false;
            }
        }

        return true;
    }

    private File getFileForProperty(Identifiable identifiable) {
        return new File(repositoryFolder.toFile().getAbsoluteFile(), identifiable.getId().toString() + DATA_FILE_EXTENSION);
    }
}
