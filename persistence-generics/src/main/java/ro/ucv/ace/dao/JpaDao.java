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
 * This interface provides methods for basic operations on JPA entities.
 *
 * @param <T>  type of the entity
 * @param <ID> type of the entity primary key
 */
public interface JpaDao<T extends BaseEntity, ID extends Serializable> {

    /**
     * Returns all entities of the type.
     *
     * @return list of entities of type T
     */
    List<T> findAll();

    /**
     * Returns a Page of entities meeting the paging restriction provided in the Page object.
     *
     * @param page page
     * @return a page of entities
     */
    List<T> findAll(Page page);

    /**
     * Returns all entities meeting the condition restriction provided in the Condition object.
     *
     * @param condition condition
     * @return list of entities meeting the condition
     */
    List<T> findAllWhere(Condition<T> condition);

    /**
     * Returns a page of entities meeting the paging restriction provided in the Page object that also meet the condition
     * restriction provided in the Condition object.
     *
     * @param condition condition
     * @param page      page
     * @return a page on entities meeting the condition
     */
    List<T> findAllWhere(Condition<T> condition, Page page);

    /**
     * Returns the entity whose id is the same as the method parameter.
     *
     * @param id id of the entity
     * @return entity
     * @throws DaoEntityNotFoundException if the entity is not found
     */
    T findOne(ID id) throws DaoEntityNotFoundException;

    /**
     * Returns the entity which meet the condition provided by the Condition object.
     *
     * @param condition condition
     * @return entity
     * @throws DaoEntityNotFoundException  if the entity is not found
     * @throws DaoNonUniqueResultException if more than one entity are found
     */
    T findOneWhere(Condition<T> condition) throws DaoEntityNotFoundException, DaoNonUniqueResultException;

    /**
     * Saves the entity.
     *
     * @param t entity to be saved
     * @return saved entity
     * @throws DaoDuplicateEntryException if a field that is set as unique is found in the database and in the entity to be saved
     * @throws DaoForeignKeyException     if the entity has a OneToOne, OneToMany or ManyToMany relationship
     *                                    and the other entity in the relationship is not found
     */
    T save(T t) throws DaoDuplicateEntryException, DaoForeignKeyException;

    /**
     * Updates the entity.
     *
     * @param t entity to be updated
     * @return updated entity
     * @throws DaoEntityNotFoundException if the entity is not found
     * @throws DaoForeignKeyException     if the entity has a OneToOne, OneToMany or ManyToMany relationship
     *                                    and the other entity in the relationship is not found
     * @throws DaoDuplicateEntryException if a field that is set as unique is found in the database and in the entity to be saved
     */
    T update(T t) throws DaoEntityNotFoundException, DaoForeignKeyException, DaoDuplicateEntryException;

    /**
     * Deletes the entity whose id is  the same as the method parameter.
     *
     * @param id id of the entity
     * @return deleted entity
     * @throws DaoEntityNotFoundException if the entity is not found
     */
    T delete(ID id) throws DaoEntityNotFoundException;

    /**
     * Deletes all the entity meeting the condition provided by the Condition object.
     *
     * @param condition condition
     * @return the number of deleted entities
     */
    Integer deleteWhere(Condition<T> condition);

    /**
     * Returns the number of entities in the database.
     *
     * @return number of entities
     */
    Long count();
}
