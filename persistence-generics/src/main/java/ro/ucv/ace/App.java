package ro.ucv.ace;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ro.ucv.ace.configuration.PersistenceConfig;
import ro.ucv.ace.domain.PageRequest;
import ro.ucv.ace.enums.Subgroup;
import ro.ucv.ace.exception.DuplicateEntryException;
import ro.ucv.ace.exception.EntityNotFoundException;
import ro.ucv.ace.exception.ForeignKeyException;
import ro.ucv.ace.exception.NonUniqueResultException;
import ro.ucv.ace.model.Student;
import ro.ucv.ace.model.Subject;
import ro.ucv.ace.service.StudentService;
import ro.ucv.ace.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
@Component
public class App {

    private StudentService studentService;

    private SubjectService subjectService;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        App app = (App) context.getBean(App.class);
        app.studentService = (StudentService) context.getBean(StudentService.class);
        app.subjectService = (SubjectService) context.getBean(SubjectService.class);

        app.insertStudents();
        app.update();
    }

    private void getSubjectsPage() {
        System.out.println(subjectService.findAll(new PageRequest(3, 6)));
    }

    private void getSubjects() {
        System.out.println(subjectService.findAll());
    }

    private void hello() {
        System.out.println("Hello world!");
    }

    private void insertStudents() {
        Student student = new Student("1940826160041", "Georgian", "Vladutu", "vladutu_georgian_4d@yahoo.com", Subgroup.A);
        Student student2 = new Student("1940826160031", "Cristian", "Totolin", "cristian_totolin@yahoo.com", Subgroup.A);

        try {
            studentService.save(student);
            studentService.save(student2);
        } catch (DuplicateEntryException e) {
            System.out.println(e.getMessage());
        } catch (ForeignKeyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findAll() {
        System.out.println(studentService.findAll());
    }

    private void findOne() {
        try {
            Student student = studentService.findOne(1);
            System.out.println(student);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findOneWhere() {
        try {
            Student student = studentService.findOneWhere(s -> s.getFirstName().equals("Georgian"));
            System.out.println(student);
        } catch (EntityNotFoundException | NonUniqueResultException e) {
            System.out.println(e.getMessage());
        }
    }

    private void update() {
        Student student = new Student("1940826160031", "Georgian", "Vladutu", "vladutu_georgian_4d@yahoo.commmmmmmm", Subgroup.A);
        student.setId(1);

        try {
            System.out.println(studentService.update(student));
        } catch (EntityNotFoundException | ForeignKeyException | DuplicateEntryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findAllWhere() {
        List<Student> students = studentService.findAllWhere(s -> s.getFirstName().equals("Georian"));

        System.out.println(students);
    }

    private void delete() {
        try {
            Student student = studentService.delete(2);
            System.out.println(student);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteWhere() {
        System.out.println(studentService.deleteWHere(s -> s.getFirstName().equals("Geogian")));
    }

    private void insertManySubjects() {
        List<Subject> subjectList = new ArrayList<>();

        subjectList.add(new Subject(1, "A", 2));
        subjectList.add(new Subject(2, "B", 3));
        subjectList.add(new Subject(3, "C", 1));
        subjectList.add(new Subject(4, "D", 5));
        subjectList.add(new Subject(5, "E", 6));
        subjectList.add(new Subject(6, "F", 3));
        subjectList.add(new Subject(7, "G", 1));
        subjectList.add(new Subject(8, "H", 4));
        subjectList.add(new Subject(9, "I", 2));
        subjectList.add(new Subject(10, "J", 5));
        subjectList.add(new Subject(11, "K", 3));
        subjectList.add(new Subject(12, "L", 1));
        subjectList.add(new Subject(13, "M", 2));
        subjectList.add(new Subject(14, "N", 4));
        subjectList.add(new Subject(15, "O", 5));
        subjectList.add(new Subject(16, "P", 6));
        subjectList.add(new Subject(17, "Q", 2));
        subjectList.add(new Subject(18, "R", 4));
        subjectList.add(new Subject(19, "S", 1));
        subjectList.add(new Subject(20, "T", 3));

        subjectList.forEach(s -> {
            try {
                subjectService.save(s);
            } catch (DuplicateEntryException | ForeignKeyException e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
