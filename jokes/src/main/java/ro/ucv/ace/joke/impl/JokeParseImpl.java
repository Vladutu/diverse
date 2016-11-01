package ro.ucv.ace.joke.impl;

import ro.ucv.ace.joke.Joke;
import ro.ucv.ace.joke.JokeBuilder;
import ro.ucv.ace.joke.JokeParser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Geo on 01.11.2016.
 */
public class JokeParseImpl implements JokeParser {

    private static final String FOLDER_NAME = "jokes/";

    private JokeBuilder jokeBuilder;

    public JokeParseImpl(JokeBuilder jokeBuilder) {
        this.jokeBuilder = jokeBuilder;
    }

    public Joke parse(File file) {
        String text = readFile(file);
        String jokeText = text.substring(text.indexOf("<!--begin of joke -->") + 22, text.indexOf("<!--end of joke -->"));
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
