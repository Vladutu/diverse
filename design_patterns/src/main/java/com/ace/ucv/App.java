package com.ace.ucv;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.Map;

public class App {

    public static void main(String[] args) throws JsonProcessingException {
        Organization organization = new Organization("1234", "EON",
                new Address("Bulevardul Bibescu", new ZipCode("4312", "200445")),
                Arrays.asList("Form1", "Form2", "Form3"));

        DotNotationExtractor dotNotationExtractor = new DotNotationExtractor();
        Map<String, Object> map = dotNotationExtractor.extract(organization);
        System.out.println(map);
        System.out.println(dotNotationExtractor.flatten(organization));
    }
}
