package ro.ucv.ace.dao.made;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.dao.JpaDaoImpl;

/**
 * Created by Geo on 28.05.2016.
 */
@Repository
public class StudentDaoImpl extends JpaDaoImpl<Student, Integer> implements StudentDao {
}
