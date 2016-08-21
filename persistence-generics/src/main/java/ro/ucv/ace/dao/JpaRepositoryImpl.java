package ro.ucv.ace.dao;

import org.jinq.jpa.JPAJinqStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.ucv.ace.configuration.JinqSource;
import ro.ucv.ace.domain.Condition;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.exception.NonUniqueResultException;
import ro.ucv.ace.model.BaseEntity;
import ro.ucv.ace.parser.ExceptionParserImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class implements JpaRepository interface.
 *
 * @param <T>  type of the entity
 * @param <ID> type of the entity primary key.
 */
public class JpaRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends AbstractRepository<T, ID> implements JpaRepository<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryImpl.class);

    private final Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JinqSource jinqSource;

    @Autowired
    private ExceptionParserImpl exceptionParser;

    public JpaRepositoryImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected JinqSource getJinqSource() {
        return this.jinqSource;
    }

    protected JPAJinqStream<T> streamAll() {
        return jinqSource.streamAll(entityManager, (Class<T>) persistentClass);
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public List<T> findAll() {
        return streamAll().toList();
    }

    @Override
    public List<T> findAll(Page page) {
        return streamAll()
                .skip(page.getSkip())
                .limit(page.getLimit())
                .toList();
    }

    @Override
    public List<T> findAllWhere(Condition<T> condition) {
        return streamAll()
                .where(condition)
                .toList();
    }

    @Override
    public List<T> findAllWhere(Condition<T> condition, Page page) {
        return streamAll()
                .skip(page.getSkip())
                .limit(page.getLimit())
                .skip(page.getSkip())
                .toList();
    }


    @Override
    public T findOne(ID id) throws EntityNotFoundException {
        T t = getEntityManager().find(persistentClass, id);

        if (t != null) {
            return t;
        }

        throw new EntityNotFoundException("Unable to find " + persistentClass.getSimpleName() + " with id " + id);
    }

    @Override
    public T findOneWhere(Condition<T> condition) throws EntityNotFoundException, NonUniqueResultException {
        Optional<T> optional;

        try {
            optional = streamAll()
                    .where(condition)
                    .findOne();
        } catch (NoSuchElementException e) {
            throw new NonUniqueResultException("Multiple entities of type " + persistentClass.getSimpleName() + " found using the searched criteria");
        }

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new EntityNotFoundException("Unable to find " + persistentClass.getSimpleName() + " using the searched criteria");

    }

    @Override
    public T save(T t) throws DuplicateEntryException, ForeignKeyException {
        try {
            return getEntityManager().merge(t);
        } catch (javax.persistence.EntityNotFoundException enfe) {
            throw new ForeignKeyException(exceptionParser.parseEntityNotFoundException(enfe));
        } catch (PersistenceException pe) {
            throw new DuplicateEntryException(exceptionParser.parsePersistenceException(pe, persistentClass));
        }
    }

    @Override
    public T update(T t) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException {
        findOne((ID) t.getId());

        try {
            T updated = getEntityManager().merge(t);
            getEntityManager().flush();

            return t;
        } catch (javax.persistence.EntityNotFoundException enfe) {
            throw new ForeignKeyException(exceptionParser.parseEntityNotFoundException(enfe));
        } catch (PersistenceException pe) {
            throw new DuplicateEntryException(exceptionParser.parsePersistenceException(pe, persistentClass));
        }
    }

    @Override
    public T delete(ID id) throws EntityNotFoundException {
        T t = findOne(id);
        getEntityManager().remove(t);

        return t;
    }

    @Override
    public Integer deleteWhere(Condition<T> condition) {
        List<T> entities = findAllWhere(condition);
        int size = entities.size();

        entities.forEach(t -> {
            getEntityManager().remove(t);
        });

        return size;
    }

    @Override
    public Long count() {
        return streamAll().count();
    }
}
