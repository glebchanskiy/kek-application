package org.glebchanskiy.dao;

import java.util.List;

public interface CrudDAO<T> {
    void insert(T entity);
    void update(T entity);
    void deleteById(int id);
    T findById(int id);
    T findByName(String name);
    void deleteByName(String name);
    List<T> findAll();
}
