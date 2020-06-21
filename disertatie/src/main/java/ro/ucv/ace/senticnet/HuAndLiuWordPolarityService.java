package ro.ucv.ace.senticnet;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ro.ucv.ace.parser.Word;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component("huAndLiuWordPolarityService")
public class HuAndLiuWordPolarityService implements PolarityService {

    private Map<String, Double> dictionary = new HashMap<>();

    public HuAndLiuWordPolarityService() {
        load();
    }

    private void load() {
        loadFile("huAndLiu/positive-words.txt", 0.5);
        loadFile("huAndLiu/negative-words.txt", -0.5);
    }

    @SneakyThrows
    private void loadFile(String file, double polarity) {
        File sentiFile = new File(getClass().getClassLoader().getResource(file).getFile());
        try (Scanner scanner = new Scanner(sentiFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dictionary.put(line, polarity);
            }
        }
    }

    @Override
    public Double findConceptPolarity(Word w1, Word w2) {
        return null;
    }

    @Override
    public Double findConceptPolarity(Word w1, Word w2, Word w3) {
        return null;
    }

    @Override
    public double findWordPolarity(Word word) {
        Double lemmaPolarity = dictionary.get(word.getLemma());
        Double valuePolarity = dictionary.get(word.getValue());

        if (valuePolarity == null && lemmaPolarity == null) {
            return 0;
        }

        if (lemmaPolarity != null) {
            return lemmaPolarity;
        }

        return valuePolarity;
    }
}
