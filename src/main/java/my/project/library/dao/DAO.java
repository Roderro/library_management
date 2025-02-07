package my.project.library.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {


    Optional<List<T>> getAll();

    Optional<T> getById(long key);

    void add(T item);

    void delete(T item);

    Optional<T> update(T item);

    void deleteByID(long key);
}