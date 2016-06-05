package ro.ucv.ace.repository;

import ro.ucv.ace.domain.Condition;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DaoDuplicateEntryException;
import ro.ucv.ace.exception.DaoEntityNotFoundException;
import ro.ucv.ace.exception.DaoNonUniqueResultException;
import ro.ucv.ace.exception.DaoRelationException;
import ro.ucv.ace.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
public interface JpaRepository<T extends BaseEntity, ID extends Serializable> {

    List<T> findAll();

    List<T> findAll(Page page);

    List<T> findAllWhere(Condition<T> condition);

    List<T> findAllWhere(Condition<T> condition, Page page);

    T findOne(ID id) throws DaoEntityNotFoundException;

    T findOneWhere(Condition<T> condition) throws DaoEntityNotFoundException, DaoNonUniqueResultException;

    T save(T t) throws DaoDuplicateEntryException, DaoRelationException;

    T update(T t) throws DaoEntityNotFoundException, DaoRelationException, DaoDuplicateEntryException;

    T delete(ID id) throws DaoEntityNotFoundException;

    Integer deleteWhere(Condition<T> condition);

    Long count();
}
