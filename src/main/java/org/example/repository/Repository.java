package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T> {
    List<T> list();
    T byId(Long id);
    void save(T t) throws SQLException;
    void delete(Long id);
}
