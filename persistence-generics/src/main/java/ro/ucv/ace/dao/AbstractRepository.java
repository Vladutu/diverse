package ro.ucv.ace.dao;

import ro.ucv.ace.domain.Condition;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.exception.NonUniqueResultException;
import ro.ucv.ace.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * This is an abstract generic repository class that specifies the methods that every class that wants to be a
 * repository of a certain entity must implement.
 *
 * @param <T>  type of the entity
 * @param <ID> type of the entity primary key
 */
public abstract class AbstractRepository<T extends BaseEntity, ID extends Serializable> {

    public abstract T save(T t) throws DuplicateEntryException, ForeignKeyException;

    public abstract List<T> findAll();

    public abstract List<T> findAll(Page page);

    public abstract List<T> findAllWhere(Condition<T> condition);

    public abstract List<T> findAllWhere(Condition<T> condition, Page page);

    public abstract T findOne(ID id) throws EntityNotFoundException;

    public abstract T findOneWhere(Condition<T> condition) throws EntityNotFoundException, NonUniqueResultException;

    public abstract T update(T t) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException;

    public abstract T delete(ID id) throws EntityNotFoundException;

    public abstract Integer deleteWhere(Condition<T> condition);

    public abstract Long count();
}
