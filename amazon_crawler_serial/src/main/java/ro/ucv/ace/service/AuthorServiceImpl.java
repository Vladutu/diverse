package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.entity.Author;
import ro.ucv.ace.repository.AuthorRepository;

import java.util.List;

/**
 * Created by Geo on 18.02.2017.
 */

@Transactional
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Integer> getAuthorIds() {
        return authorRepository.findAllIds();
    }

    @Override
    public Author getById(Integer id) {
        return authorRepository.findOne(id);
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }
}
