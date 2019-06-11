package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ucv.ace.parser.GrammarParser;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.sentiment.rule.Rule;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private GrammarParser grammarParser;

    @Autowired
    private List<Rule> rules;

    @PostMapping(name = "parseSentence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Sentence> parseSentence(@RequestBody String text) {
        Sentence sentence = grammarParser.parse(text);
        sentence.getDependencies().forEach(
                dependency -> rules.stream()
                        .filter(rule -> rule.applies(dependency))
                        .forEach(rule -> rule.execute(dependency, sentence))
        );

        return ResponseEntity.ok(sentence);
    }
}
