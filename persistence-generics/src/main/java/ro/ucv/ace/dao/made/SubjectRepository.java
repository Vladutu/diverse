package ro.ucv.ace.dao.made;

import ro.ucv.ace.domain.Page;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.model.Subject;

import java.util.List;

/**
 * Created by Geo on 04.06.2016.
 */
public interface SubjectRepository {
    void save(Subject subject) throws ForeignKeyException, DuplicateEntryException;

    List<Subject> findAll();

    List<Subject> findAll(Page page);
}
