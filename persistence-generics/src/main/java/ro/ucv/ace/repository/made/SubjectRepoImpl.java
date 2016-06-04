package ro.ucv.ace.repository.made;

import org.springframework.stereotype.Repository;
import ro.ucv.ace.model.Subject;
import ro.ucv.ace.repository.JpaRepositoryImpl;

/**
 * Created by Geo on 04.06.2016.
 */
@Repository
public class SubjectRepoImpl extends JpaRepositoryImpl<Subject, Integer> implements SubjectRepository {
}
