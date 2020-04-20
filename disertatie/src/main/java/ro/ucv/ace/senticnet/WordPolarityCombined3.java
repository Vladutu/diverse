package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

@Service("wordPolarityCombined3")
public class WordPolarityCombined3 implements WordPolarityService {

    private final WordPolarityService senticWordNetService;
    private final WordPolarityService senticNetService;
    private final WordPolarityService huAndLiuWordPolarityService;

    @Autowired
    public WordPolarityCombined3(WordPolarityService senticWordNetService, WordPolarityService senticNetService,
                                 WordPolarityService huAndLiuWordPolarityService) {
        this.senticWordNetService = senticWordNetService;
        this.senticNetService = senticNetService;
        this.huAndLiuWordPolarityService = huAndLiuWordPolarityService;
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
        double huAndLiuPolarity = huAndLiuWordPolarityService.findWordPolarity(word);
        if (huAndLiuPolarity != 0.0) {
            return huAndLiuPolarity;
        }

        double senticWordNetPolarity = senticWordNetService.findWordPolarity(word);
        if (senticWordNetPolarity != 0.0) {
            return senticWordNetPolarity;
        }

        return senticNetService.findWordPolarity(word);
    }
}
