package dao;

import java.util.ArrayList;

public interface BaseDAO<T, ID> {
    T findById(ID id);
    ArrayList<T> findAll();
    void insert(T entity);
}