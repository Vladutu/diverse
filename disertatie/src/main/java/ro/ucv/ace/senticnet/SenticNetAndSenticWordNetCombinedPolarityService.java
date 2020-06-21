package ro.ucv.ace.senticnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ucv.ace.parser.Word;

@Service("senticNetAndSenticWordNetCombinedPolarityService")
public class SenticNetAndSenticWordNetCombinedPolarityService implements PolarityService {

    private final PolarityService senticWordNetService;
    private final PolarityService senticNetService;

    @Autowired
    public SenticNetAndSenticWordNetCombinedPolarityService(PolarityService senticWordNetPolarityService, PolarityService senticNetPolarityService) {
        this.senticWordNetService = senticWordNetPolarityService;
        this.senticNetService = senticNetPolarityService;
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
