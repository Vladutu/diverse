package ro.ucv.ace.dao.made;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.ucv.ace.dao.JpaRepository;
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
@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private JpaRepository<Student, Integer> studentJpaRepository;


    @Override
    public Student save(Student student) throws ForeignKeyException, DuplicateEntryException {
        return studentJpaRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll();
    }

    @Override
    public Student findOne(Integer id) throws EntityNotFoundException {
        return studentJpaRepository.findOne(id);
    }

    @Override
    public Student update(Student student) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException {
        return studentJpaRepository.update(student);
    }

    @Override
    public List<Student> findAllWhere(Condition<Student> condition) {
        return studentJpaRepository.findAllWhere(condition);
    }

    @Override
    public Student delete(Integer id) throws EntityNotFoundException {
        return studentJpaRepository.delete(id);
    }

    @Override
    public Integer deleteWhere(Condition<Student> condition) {
        return studentJpaRepository.deleteWhere(condition);
    }

    @Override
    public Student findOneWhere(Condition<Student> studentCondition) throws NonUniqueResultException, EntityNotFoundException {
        return studentJpaRepository.findOneWhere(studentCondition);
    }
}
