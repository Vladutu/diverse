package ro.ucv.ace.senticnet;

import ro.ucv.ace.parser.Word;

public interface PolarityService {
    Double findConceptPolarity(Word word1, Word word2);

    Double findConceptPolarity(Word word1, Word word2, Word word3);

    double findWordPolarity(Word word);
}
