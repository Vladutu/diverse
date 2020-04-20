package ro.ucv.ace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.senticnet.WordPolarityService;
import ro.ucv.ace.sentiment.FallbackPolarityAlgorithm;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;
import ro.ucv.ace.sentiment.rule.*;
import ro.ucv.ace.sentiment.rule.splitSentence.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Qualifier("wordPolarityCombinedComparationService")
    @Autowired
    private WordPolarityService wordPolarityService;

    @Bean
    public List<Rule> rules() {
        return Arrays.asList(
                new SubjectNounRule(wordPolarityService, true),
                new AdjectivalAndAdverbialModifierRule(wordPolarityService, true),
                new DirectNominalObjectRule(wordPolarityService, true),
                new AdjectiveAndClausalComplementRule(wordPolarityService, true),
                new OpenClausalComplementRule(wordPolarityService, true),
                new RelativeClauseRule(wordPolarityService, true),
                new AdverbialClauseModifierRule(wordPolarityService, true),
                new UntypedDependencyRule(wordPolarityService, true),
                new AgainstRule(wordPolarityService, true)
        );
    }

    @Bean
    public List<SplitSentenceRule> splitSentenceRules() {
        return Arrays.asList(
                new ComplementClauseRule(),
                new AdverbialClauseRule(),
                new ButSplitRule(),
                new AdversariesBeginOfSentenceSplitRule(),
                new AdversariesDuringSentenceSplitRule()
        );
    }

    @Bean
    public SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm(GrammarParser grammarParser,
                                                                     FallbackPolarityAlgorithm fallbackPolarityAlgorithm) {
        return new SentimentalPolarityAlgorithm(grammarParser, rules(), fallbackPolarityAlgorithm, wordPolarityService,
                splitSentenceRules());
    }
}
