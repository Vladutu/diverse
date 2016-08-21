package ro.ucv.ace.dao.made;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.dao.JpaRepository;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.model.Subject;

import java.util.List;

/**
 * Created by Geo on 04.06.2016.
 */
@Repository
public class SubjectRepoImpl implements SubjectRepository {

    @Autowired
    private JpaRepository<Subject, Integer> subjectJpaRepository;

    @Override
    public void save(Subject subject) throws ForeignKeyException, DuplicateEntryException {
        subjectJpaRepository.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectJpaRepository.findAll();
    }

    @Override
    public List<Subject> findAll(Page page) {
        return subjectJpaRepository.findAll(page);
    }
}
