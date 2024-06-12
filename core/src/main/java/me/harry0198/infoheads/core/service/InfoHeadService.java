package me.harry0198.infoheads.core.service;

import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Business logic for handling InfoHeads database manipulation.
 * Creates and maintains a cache for rapid access of InfoHead information.
 */
public final class InfoHeadService {

    private static final Logger LOGGER = Logger.getLogger(InfoHeadService.class.getName());
    private final Repository<Set<InfoHeadProperties>> repository;
    private final Map<Location, InfoHeadProperties> cache;
    private final CompletableFuture<Void> cacheInitializationProc;

    /**
     * Class constructor.
     * Creates the cache.
     * @param repository {@link Repository} providing the InfoHead properties.
     */
    public InfoHeadService(Repository<Set<InfoHeadProperties>> repository) {
        this.repository = repository;
        this.cache = new HashMap<>();

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
     * Gets the InfoHead by the {@link Location}.
     * @param location {@link Location} to query for InfoHead.
     * @return {@link Optional<InfoHeadProperties>} of the InfoHead at the given location.
     */
    public Optional<InfoHeadProperties> getInfoHead(Location location) {
        if (location == null) return Optional.empty();

        return Optional.ofNullable(cache.get(location));
    }

    /**
     * Deletes the InfoHead from the cache and disk.
     * @param infoHeadProperties {@link InfoHeadProperties} to delete.
     */
    public void removeInfoHead(InfoHeadProperties infoHeadProperties) {
        // Remove from disk.
        CompletableFuture.runAsync(() -> repository.delete(infoHeadProperties))
                .whenComplete((ignore, error) -> {
                    LOGGER.warning("Unable to delete InfoHead");
                    LOGGER.throwing(InfoHeadService.class.getName(), "removeInfoHead", error.getCause());
                });

        // Remove from cache.
        cache.remove(infoHeadProperties.location());
    }

    private CompletableFuture<Void> initializeCache() {
        return CompletableFuture.runAsync(() -> {
            for (InfoHeadProperties infoHeadProperties : repository.get()) {
                this.cache.put(infoHeadProperties.location(), infoHeadProperties);
            }
        }).whenComplete((ignore,error) -> {
            if (error != null) {
                LOGGER.warning("Unable to generate InfoHead cache!");
                LOGGER.throwing(InfoHeadService.class.getName(), "ctor", error.getCause());
            } else {
                LOGGER.info("Successfully generated cache.");
            }
        });
    }
}
