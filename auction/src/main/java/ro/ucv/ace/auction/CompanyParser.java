package ro.ucv.ace.auction;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Geo on 21.11.2016.
 */
public class CompanyParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public CompanyParser() {
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public List<Company> parse(File file) {
        StringBuilder result = new StringBuilder("");

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Company> companies = new ArrayList<>();
        try {
            companies = objectMapper.readValue(result.toString(), new TypeReference<List<Company>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return companies;
    }

}
