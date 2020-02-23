package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

@Service("wordPolarityCombinedSenticWordNetPreferredService")
public class WordPolarityCombinedSenticWordNetPreferredService implements WordPolarityService {

    private final WordPolarityService senticWordNetService;
    private final WordPolarityService senticNetService;

    @Autowired
    public WordPolarityCombinedSenticWordNetPreferredService(WordPolarityService senticWordNetService, WordPolarityService senticNetService) {
        this.senticWordNetService = senticWordNetService;
        this.senticNetService = senticNetService;
    }


    @Override
    public Double findConceptPolarity(Word w1, Word w2) {
        return senticNetService.findConceptPolarity(w1, w2);
    }

    @Override
    public Double findConceptPolarity(Word w1, Word w2, Word w3) {
        return senticNetService.findConceptPolarity(w1, w2, w3);
    }

    @Override
    public double findWordPolarity(Word word) {
        double senticWordNetPolarity = senticWordNetService.findWordPolarity(word);
        if (senticWordNetPolarity != 0.0) {
            return senticWordNetPolarity;
        }

        return senticNetService.findWordPolarity(word);
    }
}
