package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.List;

public class AdversariesBeginOfSentenceSplitRule extends AdversariesSplitRule {

    @Override
    protected Pair<Integer, Integer> findSplitWordIndexRange(Sentence sentence) {
        int indexOfCommaAfterSubordinateClause = sentence.getParseNode().indexOfCommaAfterSubordinateClause();
        return Pair.of(indexOfCommaAfterSubordinateClause, indexOfCommaAfterSubordinateClause);
    }

    @Override
    protected int numberOfWordsToRemoveAtTheBeginningOfTheSentence(Sentence sentence) {
        return findAcceptedWords(sentence).size();
    }

    @Override
    public boolean applies(Sentence sentence) {
        if (sentence.getParseNode().indexOfCommaAfterSubordinateClause() == -1) {
            return false;
        }

        return findAcceptedWords(sentence) != null;
    }

    private List<String> findAcceptedWords(Sentence sentence) {
        List<Word> sentenceWords = sentence.getWords();
        for (List<String> words : ACCEPTED_WORDS) {
            boolean match = true;
            if (sentenceWords.size() < words.size()) {
                return null;
            }
            for (int index = 0; index < words.size(); index++) {
                if (!words.get(index).equalsIgnoreCase(sentenceWords.get(index).getValue())) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return words;
            }
        }

        return null;
    }
}
