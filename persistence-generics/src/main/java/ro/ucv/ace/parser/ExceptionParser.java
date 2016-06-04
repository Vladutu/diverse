package ro.ucv.ace.parser;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.Arrays;

/**
 * Created by Geo on 29.05.2016.
 */
@Component
public class ExceptionParser {

    public String parseForDuplicateEntry(PersistenceException e, Class<?> clazz) {
        String unformattedMessage = e.getCause().getCause().toString();
        String className = clazz.getSimpleName();
        String[] tokens = unformattedMessage.split(" ");

        return createString(className, ": ", tokens[1], " ", tokens[2], " ", tokens[3]);
    }

    public String parseForEntityNotFound(EntityNotFoundException e) {
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
