package ro.ucv.ace.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by Geo on 18.03.2017.
 */
@Component
public class ObjectFileWriter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public void writeObjectToFile(String fileName, Object value) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), value);
    }
}
