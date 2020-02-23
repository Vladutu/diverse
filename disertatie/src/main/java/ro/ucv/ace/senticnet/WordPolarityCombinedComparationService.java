package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

@Service("wordPolarityCombinedComparationService")
public class WordPolarityCombinedComparationService implements WordPolarityService {

    private final WordPolarityService senticWordNetService;
    private final WordPolarityService senticNetService;

    @Autowired
    public WordPolarityCombinedComparationService(WordPolarityService senticWordNetService, WordPolarityService senticNetService) {
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
        double senticNetPolarity = senticNetService.findWordPolarity(word);

        if (senticWordNetPolarity < 0 && senticNetPolarity > 0) {
            return senticWordNetPolarity;
        }

        return senticNetPolarity;
    }
}
