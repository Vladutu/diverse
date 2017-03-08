package ro.ucv.ace.service;

import ro.ucv.ace.entity.Author;

import java.util.List;

/**
 * Created by Geo on 18.02.2017.
 */
public interface AuthorService {

    List<Integer> getAuthorIds();

    Author getById(Integer id);

    void save(Author author);
}
