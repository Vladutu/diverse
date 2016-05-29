package ro.ucv.ace.repository;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.model.Student;

/**
 * Created by Geo on 28.05.2016.
 */
@Repository
public class StudentRepositoryImpl extends JpaRepositoryImpl<Student, Long> implements StudentRepository {
}
