package ro.ucv.ace.dao.made;

import ro.ucv.ace.domain.Condition;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.exception.NonUniqueResultException;
import ro.ucv.ace.model.Student;

import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
public interface StudentRepository{
    Student save(Student student) throws ForeignKeyException, DuplicateEntryException;

    List<Student> findAll();

    Student findOne(Integer id) throws EntityNotFoundException;

    Student update(Student student) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException;

    List<Student> findAllWhere(Condition<Student> condition);

    Student delete(Integer id) throws EntityNotFoundException;

    Integer deleteWhere(Condition<Student> condition);

    Student findOneWhere(Condition<Student> studentCondition) throws NonUniqueResultException, EntityNotFoundException;
}
