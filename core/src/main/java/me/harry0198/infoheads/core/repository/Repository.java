package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.model.InfoHeadProperties;

public interface Repository<T> {

    boolean save(InfoHeadProperties infoHeadProperties);

    T get();

    void delete(InfoHeadProperties infoHeadProperties);
}
