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
 * This interface provides methods for basic operations on JPA entities.
 *
 * @param <T>  type of the entity
 * @param <ID> type of the entity primary key
 */
public interface JpaRepository<T extends BaseEntity, ID extends Serializable> {

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
     * @throws EntityNotFoundException if the entity is not found
     */
    T findOne(ID id) throws EntityNotFoundException;

    /**
     * Returns the entity which meet the condition provided by the Condition object.
     *
     * @param condition condition
     * @return entity
     * @throws EntityNotFoundException  if the entity is not found
     * @throws NonUniqueResultException if more than one entity are found
     */
    T findOneWhere(Condition<T> condition) throws EntityNotFoundException, NonUniqueResultException;

    /**
     * Saves the entity.
     *
     * @param t entity to be saved
     * @return saved entity
     * @throws DuplicateEntryException if a field that is set as unique is found in the database and in the entity to be saved
     * @throws ForeignKeyException     if the entity has a OneToOne, OneToMany or ManyToMany relationship
     *                                    and the other entity in the relationship is not found
     */
    T save(T t) throws DuplicateEntryException, ForeignKeyException;

    /**
     * Updates the entity.
     *
     * @param t entity to be updated
     * @return updated entity
     * @throws EntityNotFoundException if the entity is not found
     * @throws ForeignKeyException     if the entity has a OneToOne, OneToMany or ManyToMany relationship
     *                                    and the other entity in the relationship is not found
     * @throws DuplicateEntryException if a field that is set as unique is found in the database and in the entity to be saved
     */
    T update(T t) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException;

    /**
     * Deletes the entity whose id is  the same as the method parameter.
     *
     * @param id id of the entity
     * @return deleted entity
     * @throws EntityNotFoundException if the entity is not found
     */
    T delete(ID id) throws EntityNotFoundException;

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
