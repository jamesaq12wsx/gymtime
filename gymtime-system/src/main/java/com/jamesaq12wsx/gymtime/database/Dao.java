package com.jamesaq12wsx.gymtime.database;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao<T, K> {

    Optional<T> get(UUID id);

    List<T> getAll();

    void save(T t);

    void saveAll(List<? extends T> tList);

    void update(T t);

    void delete(K id);
}
