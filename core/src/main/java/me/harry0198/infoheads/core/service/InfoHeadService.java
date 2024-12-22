package me.harry0198.infoheads.core.service;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.persistence.entity.Identifiable;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.Repository;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Business logic for handling InfoHeads database manipulation.
 * Creates and maintains a cache for rapid access of InfoHead information.
 */
public class InfoHeadService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final Repository<InfoHeadProperties> infoHeadRepository;
    private final Map<Location, InfoHeadProperties> cache;
    private final CompletableFuture<Void> cacheInitializationProc;

    /**
     * Class constructor.
     * Creates the cache.
     * @param infoHeadRepository {@link Repository} providing the InfoHead properties.
     */
    @Inject
    public InfoHeadService(Repository<InfoHeadProperties> infoHeadRepository) {
        this.infoHeadRepository = infoHeadRepository;
        this.cache = new ConcurrentHashMap<>();

        // Generate cache
        this.cacheInitializationProc = initializeCache();
    }

    /**
     * Checks if the cache is currently being initialized and populated or not.
     * @return If the cache is currently initialized and populated or not.
     */
    public boolean isCacheInitialized() {
        return cacheInitializationProc.isDone();
    }

    /**
     * Adds infohead to the repository and cache. Overwrites if already exists at location as one location should
     * hold one InfoHead.
     * @param infoHeadProperties {@link InfoHeadProperties} to add.
     * @return a {@link CompletableFuture} that resolves to {@code true} if the properties were successfully added,
     *         or {@code false} otherwise.
     */
    public CompletableFuture<Boolean> addInfoHead(InfoHeadProperties infoHeadProperties) {
        return CompletableFuture.supplyAsync(() -> {
            boolean didSave = infoHeadRepository.save(infoHeadProperties);

            if (didSave) {
                this.cache.put(infoHeadProperties.getLocation(), infoHeadProperties);
            }

            return didSave;
        });
    }

    public void clearLocation(Location location) {
        if (location == null) return;
        this.cache.remove(location);
    }

    public Collection<InfoHeadProperties> getAll() {
        return cache.values();
    }

    /**
     * Gets the InfoHead by the {@link Location}.
     * @param location {@link Location} to query for InfoHead.
     * @return {@link Optional<InfoHeadProperties>} of the InfoHead at the given location.
     */
    public Optional<InfoHeadProperties> getInfoHead(Location location) {
        if (location == null) return Optional.empty();

        return Optional.ofNullable(cache.get(location));
    }

    /**
     * Gets the infohead by its {@link Identifiable#getId()}.
     * @param uuid to fetch infohead by.
     * @return {@link Optional} of the infohead by the given id.
     */
    public Optional<InfoHeadProperties> getInfoHead(UUID uuid) {
        if (uuid == null) return Optional.empty();
        return cache.values().stream().filter(x -> x.getId().equals(uuid)).findFirst();
    }

    /**
     * Deletes the InfoHead from the cache and disk.
     * @param infoHeadProperties {@link InfoHeadProperties} to delete.
     * @return a {@link CompletableFuture} that resolves to {@code true} if the properties were successfully deleted,
     *         or {@code false} otherwise.
     */
    public CompletableFuture<Boolean> removeInfoHead(InfoHeadProperties infoHeadProperties) {
        // Remove from disk.
        return CompletableFuture.supplyAsync(() -> {
            boolean didDelete = infoHeadRepository.delete(infoHeadProperties);
            if (didDelete) {
                cache.remove(infoHeadProperties.getLocation());
            }

            return didDelete;
        });
    }

    public CompletableFuture<Boolean> saveInfoHeadToRepository(InfoHeadProperties infoHeadProperties) {
        return CompletableFuture.supplyAsync(() -> infoHeadRepository.save(infoHeadProperties));
    }

    public CompletableFuture<Void> saveCacheToRepository() {
        List<CompletableFuture<Boolean>> futures = cache.values().stream().map(this::saveInfoHeadToRepository).toList();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private CompletableFuture<Void> initializeCache() {
        return CompletableFuture.runAsync(() -> {
            for (InfoHeadProperties infoHeadProperties : infoHeadRepository.getAll()) {
                this.cache.put(infoHeadProperties.getLocation(), infoHeadProperties);
            }
        }).whenComplete((ignore,error) -> {
            if (error != null) {
                LOGGER.warn("Unable to generate InfoHead cache!");
                LOGGER.debug("ctor", error.getCause());
            } else {
                LOGGER.info("Generated InfoHead cache.");
            }
        });
    }
}
