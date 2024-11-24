package me.harry0198.infoheads.core.persistence.repository;

import me.harry0198.infoheads.core.persistence.entity.Identifiable;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

import java.io.Serializable;
import java.nio.file.Path;

public class RepositoryFactory {

    private RepositoryFactory() {}

    public static <T extends Serializable & Identifiable> Repository<T> getRepository(Path workingDirectory) {
        return new LocalRepository<>(workingDirectory.resolve("heads"), InfoHeadProperties.class);
    }
}
