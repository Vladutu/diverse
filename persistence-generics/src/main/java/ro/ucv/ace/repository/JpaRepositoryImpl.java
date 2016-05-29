package ro.ucv.ace.repository;

import org.jinq.jpa.JPAJinqStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.ucv.ace.configuration.JinqSource;
import ro.ucv.ace.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Geo on 28.05.2016.
 */
public class JpaRepositoryImpl<T, ID> extends AbstractRepository<T, ID> implements JpaRepository<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryImpl.class);

    private final Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JinqSource jinqSource;

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
    public T save(T t) throws DaoException {
        try {
            return getEntityManager().merge(t);
        } catch (EntityNotFoundException enfe) {
            throw new DaoException(enfe.getMessage());
        } catch (PersistenceException pe) {
            throw new DaoException(pe.getCause().getCause().toString());
        }

    }
}
