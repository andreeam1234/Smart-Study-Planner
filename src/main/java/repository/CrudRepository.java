package repository;

import java.util.List;

public interface CrudRepository<T> {
    void create(T object);
    T read(int id);
    List<T> readAll();
    void update(T object);
    void delete(int id);
}