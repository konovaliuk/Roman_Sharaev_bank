package roman.ironbank.model.dao;

import java.util.LinkedList;

public interface GenericDao<T> {

    int create(T entity);

    LinkedList<T> findAll();

    T findById(int id);


    boolean update(T entity);

    boolean delete(T entity);
}
