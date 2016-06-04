package ro.ucv.ace.repository.made;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.repository.JpaRepositoryImpl;

/**
 * Created by Geo on 28.05.2016.
 */
@Repository
public class StudentRepositoryImpl extends JpaRepositoryImpl<Student, Integer> implements StudentRepository {
}
