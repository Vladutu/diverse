package ro.ucv.ace.parser;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

/**
 * Interface which provides methods to modify the plain messages returned by the JPA exceptions.
 *
 * @author Georgian Vladutu
 */
public interface ExceptionParser {

    /**
     * Parses the message of a PersistenceException object and returns the new message.
     *
     * @param e     PersistenceException object
     * @param clazz the class of the entity which caused the exception
     * @return String the new message
     */
    String parsePersistenceException(PersistenceException e, Class<?> clazz);

    /**
     * Parses the message of a EntityNotFoundException object and returns the new message.
     *
     * @param e EntityNotFoundException object
     * @return String the new message
     */
    String parseEntityNotFoundException(EntityNotFoundException e);

}
