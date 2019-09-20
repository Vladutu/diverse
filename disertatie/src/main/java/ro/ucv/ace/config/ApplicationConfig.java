package ro.ucv.ace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public List<Rule> rules(SenticNetService senticNetService) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new SubjectNounRule(senticNetService, true));
        rules.add(new AdjectivalAndAdverbialModifierRule(senticNetService, true));
        rules.add(new DirectNominalObjectRule(senticNetService, true));
        rules.add(new AdjectiveAndClausalComplementRule(senticNetService, true));
        rules.add(new OpenClausalComplementRule(senticNetService, true));
        rules.add(new RelativeClauseRule(senticNetService, true));
        rules.add(new AdverbialClauseModifierRule(senticNetService, true));
        rules.add(new UntypedDependencyRule(senticNetService, true));
        rules.add(new AgainstRule(senticNetService, true));

        return rules;
    }
}
