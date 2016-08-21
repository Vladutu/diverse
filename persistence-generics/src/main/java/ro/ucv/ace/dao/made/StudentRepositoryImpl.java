package ro.ucv.ace.dao.made;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.dao.JpaRepositoryImpl;
import ro.ucv.ace.model.Student;

/**
 * Created by Geo on 28.05.2016.
 */
@Repository
public class StudentRepositoryImpl extends JpaRepositoryImpl<Student, Integer> implements StudentRepository {
}
