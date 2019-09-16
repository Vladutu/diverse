package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ucv.ace.parser.Sentence;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

@RestController
public class TestController {

    @Autowired
    private SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;

    @PostMapping(name = "parseSentence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Sentence> parseSentence(@RequestBody String text) {
        return ResponseEntity.ok(sentimentalPolarityAlgorithm.execute(text));
    }
}
