package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

@Service("wordPolarityCombined3")
public class WordPolarityCombined3 implements PolarityService {

    private final PolarityService senticWordNetService;
    private final PolarityService senticNetService;
    private final PolarityService huAndLiuWordPolarityService;

    @Autowired
    public WordPolarityCombined3(PolarityService senticWordNetPolarityService, PolarityService senticNetPolarityService,
                                 PolarityService huAndLiuWordPolarityService) {
        this.senticWordNetService = senticWordNetPolarityService;
        this.senticNetService = senticNetPolarityService;
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
