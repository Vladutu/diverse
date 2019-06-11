package ro.ucv.ace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ucv.ace.senticnet.SenticNetService;
import ro.ucv.ace.sentiment.rule.DirectNominalObjectRule;
import ro.ucv.ace.sentiment.rule.Rule;
import ro.ucv.ace.sentiment.rule.SubjectNounRule;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public List<Rule> rules(SenticNetService senticNetService) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new SubjectNounRule(senticNetService));
        rules.add(new DirectNominalObjectRule(senticNetService));

        return rules;
    }
}
