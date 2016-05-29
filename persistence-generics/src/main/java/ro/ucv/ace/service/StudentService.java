package ro.ucv.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ucv.ace.exception.DaoException;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.repository.StudentRepository;

/**
 * Created by Geo on 28.05.2016.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) throws DaoException {
        return studentRepository.save(student);
    }
}
