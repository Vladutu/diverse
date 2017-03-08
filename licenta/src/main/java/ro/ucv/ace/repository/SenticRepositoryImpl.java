package ro.ucv.ace.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ro.ucv.ace.model.SenticConcept;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Geo on 05.03.2017.
 */
public class SenticRepositoryImpl implements SenticRepository {

    private Map<String, SenticConcept> senticConcepts = new HashMap<>();

    public SenticRepositoryImpl() {
        File file = new File(getClass().getClassLoader().getResource("sentic.json").getFile());
        try {

            ObjectMapper mapper = new ObjectMapper();
            TypeFactory typeFactory = mapper.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, SenticConcept.class);
            senticConcepts = mapper.<HashMap<String, SenticConcept>>readValue(file, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SenticConcept> findConcept(String name) {
        SenticConcept concept = senticConcepts.get(name);
        if (concept != null) {
            return Optional.of(concept);
        }

        return Optional.empty();
    }
}
