package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        saveData();
        getStudentCourseList();
    }

    public static void getStudentCourseList() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Query<Object[]> query = session.createQuery("select s.id, s.name, s.surname, " +
                " c.id, c.title, c.price " +
                " from StudentEntity s " +
                " inner join CourseEntity c on c.id = s.courseId ");
        List<Object[]> list = query.list();

        for (Object[] obj : list) {
            Integer studentId = (Integer) obj[0];
            String studentName = (String) obj[1];
            String studentSurname = (String) obj[2];

            Integer courseId = (Integer) obj[3];
            String title = (String) obj[4];
            Double price = (Double) obj[5];

            System.out.println(studentId + " " + studentName + " " + studentSurname + " " + courseId + " " + title + " " + price);
        }

        factory.close();
        session.close();
    }


    public static void saveData() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        // course
        CourseEntity course1 = new CourseEntity();
        course1.setTitle("Java");
        course1.setPrice(25.0);
        session.save(course1);

        CourseEntity course2 = new CourseEntity();
        course2.setTitle("English");
        course2.setPrice(15.0);
        session.save(course2);

        // students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Ali");
        student1.setSurname("Aliyev");
        student1.setCourseId(course1.getId());
        session.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Valish");
        student2.setSurname("Valiyev");
        student2.setCourseId(course2.getId());
        session.save(student2);

        StudentEntity student3 = new StudentEntity();
        student3.setName("Toshmat");
        student3.setSurname("Toshmatov");
        student3.setCourseId(course1.getId());
        session.save(student3);

        t.commit();

        factory.close();
        session.close();
    }
}