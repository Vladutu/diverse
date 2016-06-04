package ro.ucv.ace.repository;

import ro.ucv.ace.exception.DaoDuplicateEntryException;
import ro.ucv.ace.exception.DaoEntityNotFoundException;
import ro.ucv.ace.exception.DaoRelationException;
import ro.ucv.ace.model.BaseEntity;
import ro.ucv.ace.repository.misc.Condition;
import ro.ucv.ace.repository.misc.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
public abstract class AbstractRepository<T extends BaseEntity, ID extends Serializable> {

    public abstract T save(T t) throws DaoDuplicateEntryException, DaoRelationException;

    public abstract List<T> findAll();

    public abstract List<T> findAll(Page page);

    public abstract List<T> findAllWhere(Condition<T> condition);

    public abstract List<T> findAllWhere(Condition<T> condition, Page page);

    public abstract T findOne(ID id) throws DaoEntityNotFoundException;

    public abstract T update(T t) throws DaoEntityNotFoundException, DaoRelationException, DaoDuplicateEntryException;

    public abstract T delete(ID id) throws DaoEntityNotFoundException;

    public abstract Integer deleteWhere(Condition<T> condition);

    public abstract Long count();
}
