package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.exception.DaoDuplicateEntryException;
import ro.ucv.ace.exception.DaoEntityNotFoundException;
import ro.ucv.ace.exception.DaoException;
import ro.ucv.ace.exception.DaoRelationException;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.repository.made.StudentRepository;
import ro.ucv.ace.domain.Condition;

import java.util.List;

/**
 * Created by Geo on 28.05.2016.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) throws DaoDuplicateEntryException, DaoRelationException {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findOne(Integer id) throws DaoEntityNotFoundException {
        return studentRepository.findOne(id);
    }

    public Student update(Student student) throws DaoEntityNotFoundException, DaoRelationException, DaoDuplicateEntryException {
        Student student1 = studentRepository.update(student);

        int x = 3;

        return student;
    }

    public List<Student> findAllWhere(Condition<Student> condition) {
        return studentRepository.findAllWhere(condition);
    }

    public Student delete(Integer id) throws DaoEntityNotFoundException {
        return studentRepository.delete(id);
    }

    public Integer deleteWHere(Condition<Student> condition) {
        return studentRepository.deleteWhere(condition);
    }
}
