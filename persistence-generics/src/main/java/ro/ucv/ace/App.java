package ro.ucv.ace;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.configuration.PersistenceConfig;
import ro.ucv.ace.enums.Subgroup;
import ro.ucv.ace.exception.DaoException;
import ro.ucv.ace.model.Group;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.service.StudentService;

/**
 * Hello world!
 */
@Component
public class App {

    private StudentService studentService;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        App app = (App) context.getBean(App.class);
        app.studentService = (StudentService) context.getBean(StudentService.class);

        app.hello();
        app.insertStudents();
    }

    private void hello() {
        System.out.println("Hello world!");
    }

    private void insertStudents() {
        Student student = new Student("1940826160041", "Georgian", "Vladutu", "vladutu_georgian_4d@yahoo.com", Subgroup.A);
        Group group = new Group();
        group.setId(1L);
        group.setName("C.E.");
        //student.setGroup(group);

        try {
            studentService.save(student);
            studentService.save(student);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        //studentService.save(student);
    }

}
