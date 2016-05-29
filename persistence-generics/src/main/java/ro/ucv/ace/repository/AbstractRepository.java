package ro.ucv.ace.repository;

import ro.ucv.ace.exception.DaoException;

/**
 * Created by Geo on 28.05.2016.
 */
public abstract class AbstractRepository<T, ID> {
    public abstract T save(T t) throws DaoException;
}
