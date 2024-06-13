package me.harry0198.infoheads.core.repository;

import java.util.List;

public interface Repository<T> {

    boolean save(T obj);

    List<T> getAll();

    boolean delete(T obj);
}
