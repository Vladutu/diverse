package ro.ucv.ace.parser;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.Arrays;

/**
 * This class is a parser used to modify the plain messages returned by the JPA exceptions.
 *
 * @author Georgian Vladutu
 */
@Component
public class ExceptionParser {

    /**
     * Parses the message of a PersistenceException object and returns the new message.
     *
     * @param e     PersistenceException object
     * @param clazz the class of the entity which caused the exception
     * @return String the new message
     */
    public String parsePersistenceException(PersistenceException e, Class<?> clazz) {
        String unformattedMessage = e.getCause().getCause().toString();
        String className = clazz.getSimpleName();
        String[] tokens = unformattedMessage.split(" ");

        return createString(className, ": ", tokens[1], " ", tokens[2], " ", tokens[3]);
    }

    /**
     * Parses the message of a EntityNotFoundException object and returns the new message.
     *
     * @param e EntityNotFoundException object
     * @return String the new message
     */
    public String parseEntityNotFoundException(EntityNotFoundException e) {
        String unformattedMessage = e.getMessage();
        String tokens[] = unformattedMessage.split(" ");
        String className = tokens[3].split("\\.")[4];
        System.out.println(Arrays.toString(tokens));

        return createString("Unable to find " + className + " with id " + tokens[6]);
    }

    private String createString(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }
}
