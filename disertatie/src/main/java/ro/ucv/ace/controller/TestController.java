package ro.ucv.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ucv.ace.sentiment.SentimentalPolarityAlgorithm;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController("/test")
public class TestController {

    @Autowired
    private SentimentalPolarityAlgorithm sentimentalPolarityAlgorithm;

    @PostMapping(name = "/parseSentence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> parseSentence(@RequestBody String text) {
        return ResponseEntity.ok(sentimentalPolarityAlgorithm.execute(text));
    }

    @GetMapping(name = "/executeTests")
    public ResponseEntity<List<Pair<String, String>>> executeTests() {
        List<Pair<String, String>> result = provideValues()
                .map(pair -> executeTest(pair.getFirst(), pair.getSecond()))
                .filter(pair -> pair.getSecond().equals("WRONG"))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    private Pair<String, String> executeTest(String input, Boolean output) {
        Double result = sentimentalPolarityAlgorithm.execute(input);
        boolean correct = (result < 0) == output;
        String resultText = correct ? "CORRECT" : "WRONG";

        return Pair.of(input, resultText);
    }

    private Stream<Pair<String, Boolean>> provideValues() {
        return Stream.of(
                Pair.of("The movie is boring.", true),
                Pair.of("His troubles were relieved.", false),
                Pair.of("My success has pissed him off.", true),
                Pair.of("Her gift was bad.", true),
                Pair.of("Paul saw the movie in 3D.", false),
                Pair.of("You made me pissed off.", true),
                Pair.of("John loves this boring movie.", true),
                Pair.of("You have hurt the cute cat", true),
                Pair.of("I love that you did not win the match.", false),
                Pair.of("I do not know whether he is good", true),
                Pair.of("This meal smells bad.", true),
                Pair.of("This is perfect to gain money.", false),
                Pair.of("This is perfect to gain weight", true),
                Pair.of("This is perfect to lose money.", true),
                Pair.of("This is perfect to lose weight", false),
                Pair.of("This is useless to gain money ", true),
                Pair.of("This is useless to gain money", true),
                Pair.of("This is useless to lose money.", true),
                Pair.of("This is useless to lose weight.", true),
                Pair.of("This is perfect to talk about money.", false),
                Pair.of("This is perfect to talk about weight.", true),
                Pair.of("This is useless to talk about money", true),
                Pair.of("This is useless to talk about weight", true),
                Pair.of("Paul is a bad loser", true),
                Pair.of("Mary is beautifully depressed.", false),
                Pair.of("I saw the movie you love.", false),
                Pair.of("I liked the movie you love.", false),
                Pair.of("I disliked the movie you love.", true),
                Pair.of("The machine slows down when the best games are playing.", true),
                Pair.of("My failure makes him happy", true),
                Pair.of("While I’m sure the quality of the product is fine, the color is very different.", true),
                Pair.of("This car is nice but expensive.", true),
                Pair.of("This car is expensive but nice", false),
                Pair.of("The movie is not only boring but also offensive.", true),
                Pair.of("Even though he's a millionaire, he lives in a very small flat.", true),
                Pair.of("Despite being a millionaire, he lives in a very small flat.", true),
                Pair.of("He won the race even though he's much older than the others.", false),
                Pair.of("He won the race although he's much older than the others.", false)
        );
    }
}
