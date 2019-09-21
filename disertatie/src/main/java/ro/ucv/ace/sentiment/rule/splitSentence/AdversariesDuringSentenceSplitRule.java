package ro.ucv.ace.sentiment.rule.splitSentence;

import org.springframework.data.util.Pair;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.parser.Word;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class AdversariesDuringSentenceSplitRule extends AdversariesSplitRule {

    @Override
    protected Pair<Integer, Integer> findSplitWordIndexRange(Sentence sentence) {
        Pair<Word, List<String>> pair = findAcceptedWords(sentence);
        Word firstWord = pair.getFirst();
        List<String> acceptedWords = pair.getSecond();

        return Pair.of(firstWord.getIndex(), firstWord.getIndex() + acceptedWords.size() - 1);
    }

    @Override
    protected int numberOfWordsToRemoveAtTheBeginningOfTheSentence(Sentence sentence) {
        return 0;
    }

    @Override
    public boolean applies(Sentence sentence) {
        return findAcceptedWords(sentence) != null;
    }

    @Override
    protected Double executeAlgorithmOnSentences(Sentence first, Sentence second, Function<Sentence, Double> algorithmFunction) {
        Double firstPolarity = algorithmFunction.apply(first);
        Double secondPolarity = algorithmFunction.apply(second);

        if (firstPolarity != null && firstPolarity != 0) {
            return firstPolarity;
        }

        if (secondPolarity == null) {
            return 0.0;
        }

        return -secondPolarity;
    }

    private Pair<Word, List<String>> findAcceptedWords(Sentence sentence) {
        List<Word> sentenceWords = sentence.getWords();
        for (List<String> words : ACCEPTED_WORDS) {
            Word firstWord = findAcceptedWord(sentence, Collections.singletonList(words.get(0)));
            if (firstWord == null) {
                continue;
            }
            if (sentenceWords.size() < firstWord.getIndex() + words.size()) {
                continue;
            }

            boolean match = true;
            for (int index = 1; index < words.size(); index++) {
                if (!words.get(index).equalsIgnoreCase(sentenceWords.get(firstWord.getIndex() - 1 + index).getValue())) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return Pair.of(firstWord, words);
            }
        }

        return null;
    }
}
