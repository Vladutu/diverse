package ro.ucv.ace.dao;

import ro.ucv.ace.domain.Condition;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DaoDuplicateEntryException;
import ro.ucv.ace.exception.DaoEntityNotFoundException;
import ro.ucv.ace.exception.DaoForeignKeyException;
import ro.ucv.ace.exception.DaoNonUniqueResultException;
import ro.ucv.ace.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * This is an abstract generic data access object class that specifies the methods that every class that wants to be a
 * DAO of a certain entity must implement.
 *
 * @param <T>  type of the entity
 * @param <ID> type of the entity primary key
 */
public abstract class AbstractDao<T extends BaseEntity, ID extends Serializable> {

    public abstract T save(T t) throws DaoDuplicateEntryException, DaoForeignKeyException;

    public abstract List<T> findAll();

    public abstract List<T> findAll(Page page);

    public abstract List<T> findAllWhere(Condition<T> condition);

    public abstract List<T> findAllWhere(Condition<T> condition, Page page);

    public abstract T findOne(ID id) throws DaoEntityNotFoundException;

    public abstract T findOneWhere(Condition<T> condition) throws DaoEntityNotFoundException, DaoNonUniqueResultException;

    public abstract T update(T t) throws DaoEntityNotFoundException, DaoForeignKeyException, DaoDuplicateEntryException;

    public abstract T delete(ID id) throws DaoEntityNotFoundException;

    public abstract Integer deleteWhere(Condition<T> condition);

    public abstract Long count();
}
