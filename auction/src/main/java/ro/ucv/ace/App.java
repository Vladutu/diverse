package ro.ucv.ace;

import ro.ucv.ace.auction.Company;
import ro.ucv.ace.auction.CompanyParser;

import java.io.File;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        File file = new File(App.class.getClassLoader().getResource("companies.json").getFile());
        CompanyParser companyParser = new CompanyParser();

        List<Company> companies = companyParser.parse(file);
        companies.forEach(System.out::println);
    }
}
