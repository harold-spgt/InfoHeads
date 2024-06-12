package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.model.InfoHeadProperties;

public class LocalRepository<T> implements Repository<T> {


    @Override
    public boolean save(InfoHeadProperties infoHeadProperties) {
        return false;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public void delete(InfoHeadProperties infoHeadProperties) {

    }

}
