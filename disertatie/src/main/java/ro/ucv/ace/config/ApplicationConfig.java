package ro.ucv.ace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.*;
import ro.ucv.ace.sentiment.rule.splitSentence.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public List<Rule> rules(SenticNetService senticNetService) {
        return Arrays.asList(
                new SubjectNounRule(senticNetService, true),
                new AdjectivalAndAdverbialModifierRule(senticNetService, true),
                new DirectNominalObjectRule(senticNetService, true),
                new AdjectiveAndClausalComplementRule(senticNetService, true),
                new OpenClausalComplementRule(senticNetService, true),
                new RelativeClauseRule(senticNetService, true),
                new AdverbialClauseModifierRule(senticNetService, true),
                new UntypedDependencyRule(senticNetService, true),
                new AgainstRule(senticNetService, true)
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
}
