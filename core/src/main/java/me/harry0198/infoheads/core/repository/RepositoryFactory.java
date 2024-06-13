package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.model.Identifiable;

import java.io.Serializable;
import java.nio.file.Path;

public class RepositoryFactory{

    public static <T extends Serializable & Identifiable> Repository<T> getRepository() {
        return new LocalRepository<>(Path.of("")); //TODO
    }
}
