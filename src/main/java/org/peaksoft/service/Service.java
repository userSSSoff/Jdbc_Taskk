package org.peaksoft.service;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {

    void createTable();

    void dropTable() throws SQLException;

    void save(T t);

    void removeById(long id);

    List<T> getAll();

    void cleanTable();
    T getById(long id);

}

