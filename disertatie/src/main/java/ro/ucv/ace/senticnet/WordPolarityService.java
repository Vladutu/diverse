package ro.ucv.ace.senticnet;

import ro.ucv.ace.parser.Word;

public interface WordPolarityService {
    Double findConceptPolarity(Word w1, Word w2);

    Double findConceptPolarity(Word w1, Word w2, Word w3);

    double findWordPolarity(Word word);
}
