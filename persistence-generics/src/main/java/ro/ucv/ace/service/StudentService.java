package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.dao.made.StudentRepository;
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
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) throws DuplicateEntryException, ForeignKeyException {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findOne(Integer id) throws EntityNotFoundException {
        return studentRepository.findOne(id);
    }

    public Student update(Student student) throws EntityNotFoundException, ForeignKeyException, DuplicateEntryException {
        Student student1 = studentRepository.update(student);

        int x = 3;

        return student;
    }

    public List<Student> findAllWhere(Condition<Student> condition) {
        return studentRepository.findAllWhere(condition);
    }

    public Student delete(Integer id) throws EntityNotFoundException {
        return studentRepository.delete(id);
    }

    public Integer deleteWHere(Condition<Student> condition) {
        return studentRepository.deleteWhere(condition);
    }

    public Student findOneWhere(Condition<Student> studentCondition) throws EntityNotFoundException, NonUniqueResultException {
        return studentRepository.findOneWhere(studentCondition);
    }
}
