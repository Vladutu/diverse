package ro.ucv.ace.dao.made;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.model.Subject;
import ro.ucv.ace.dao.JpaDaoImpl;

/**
 * Created by Geo on 04.06.2016.
 */
@Repository
public class SubjectRepoImpl extends JpaDaoImpl<Subject, Integer> implements SubjectDao {
}
