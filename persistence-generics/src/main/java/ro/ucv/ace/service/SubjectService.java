package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.dao.made.SubjectRepository;
import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.model.Subject;

import java.util.List;

/**
 * Created by Geo on 04.06.2016.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public void save(Subject subject) throws DuplicateEntryException, ForeignKeyException {
        subjectRepository.save(subject);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> findAll(Page page) {
        return subjectRepository.findAll(page);
    }
}
