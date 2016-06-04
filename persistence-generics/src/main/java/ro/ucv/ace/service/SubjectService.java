package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.exception.DaoDuplicateEntryException;
import ro.ucv.ace.exception.DaoException;
import ro.ucv.ace.exception.DaoRelationException;
import ro.ucv.ace.model.Subject;
import ro.ucv.ace.repository.made.SubjectRepository;
import ro.ucv.ace.repository.misc.Page;

import java.util.List;

/**
 * Created by Geo on 04.06.2016.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public void save(Subject subject) throws DaoDuplicateEntryException, DaoRelationException {
        subjectRepository.save(subject);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> findAll(Page page) {
        return subjectRepository.findAll(page);
    }
}
