package me.harry0198.infoheads.core.repository;

public class RepositoryFactory{

    public static <T> Repository<T> getRepository() {
        return new LocalRepository<>();
    }
}
