package ro.ucv.ace;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Geo on 08.11.2016.
 */
public class JokeParser {

    private JokeBuilder jokeBuilder;

    private static final String BEGIN_JOKE = "<!--begin of joke -->";

    private static final String END_JOKE = "<!--end of joke -->";

    public JokeParser(JokeBuilder jokeBuilder) {
        this.jokeBuilder = jokeBuilder;
    }

    public Joke parse(File file) {
        String text = readFile(file);
        String jokeText = text.substring(text.indexOf(BEGIN_JOKE) + 22, text.indexOf(END_JOKE));
        jokeText = jokeText.replaceAll("<P>", "").replaceAll("<p>", "");

        return jokeBuilder.build(jokeText);
    }

    private String readFile(File file) {
        StringBuilder result = new StringBuilder("");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
