package ro.ucv.ace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.senticnet.PolarityService;
import ro.ucv.ace.sentiment.FallbackPolarityAlgorithm;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;
import ro.ucv.ace.sentiment.rule.*;
import ro.ucv.ace.sentiment.rule.splitSentence.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Qualifier("senticNetAndSenticWordNetCombinedPolarityService")
    @Autowired
    private PolarityService polarityService;

    @Bean
    public List<Rule> rules() {
        return Arrays.asList(
                new SubjectNounRule(polarityService, true),
                new AdjectivalAndAdverbialModifierRule(polarityService, true),
                new DirectNominalObjectRule(polarityService, true),
                new AdjectiveAndClausalComplementRule(polarityService, true),
                new OpenClausalComplementRule(polarityService, true),
                new RelativeClauseRule(polarityService, true),
                new AdverbialClauseModifierRule(polarityService, true),
                new UntypedDependencyRule(polarityService, true),
                new AgainstRule(polarityService, true)
        );
    }

    @Bean
    public List<SentenceSplitRule> sentenceSplitRules() {
        return Arrays.asList(
                new ComplementClauseSplitRule(),
                new AdverbialClauseSplitRule(),
                new ButSplitRule(),
                new AdversativeBeginOfSentenceSplitRule(),
                new AdversativeDuringSentenceSplitRule()
        );
    }

    @Bean
    public SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm(GrammarParser grammarParser,
                                                                     FallbackPolarityAlgorithm fallbackPolarityAlgorithm) {
        return new SentimentalPolarityAlgorithm(grammarParser, rules(), fallbackPolarityAlgorithm, polarityService,
                sentenceSplitRules());
    }

    @Bean
    public FallbackPolarityAlgorithm fallbackPolarityAlgorithm() {
        return new FallbackPolarityAlgorithm(polarityService);
    }
}
