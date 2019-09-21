package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.Collections;

public class ButSplitRule extends AdversariesSplitRule {

    private static final String ACCEPTED_WORD = "but";
    private static final String REJECTED_WORD = "also";

    @Override
    protected Pair<Integer, Integer> findSplitWordIndexRange(Sentence sentence) {
        int index = findAcceptedWord(sentence, Collections.singletonList(ACCEPTED_WORD)).getIndex();
        return Pair.of(index, index);
    }

    @Override
    protected int numberOfWordsToRemoveAtTheBeginningOfTheSentence(Sentence sentence) {
        return 0;
    }

    @Override
    public boolean applies(Sentence sentence) {
        Word word = findAcceptedWord(sentence, Collections.singletonList(ACCEPTED_WORD));
        if (word == null) {
            return false;
        }
        Word nextWord = sentence.getWords().get(word.getIndex());

        return !nextWord.getValue().equalsIgnoreCase(REJECTED_WORD);
    }
}
