import org.checkerframework.checker.units.qual.C;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {

   private static String courseName;
     private static String studentName;
   public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();


        String hqlCourse = "From " + Course.class.getSimpleName() + " Where name='" + courseName + "'";
        String hqlStudent = "From " + Student.class.getSimpleName();
        String hqlPurchase = " From " + Purchase.class.getSimpleName() + " Where course_name='Веб-разработчик c 0 до PRO'";

       CriteriaBuilder builder = session.getCriteriaBuilder();
       CriteriaQuery<Purchase> query = builder.createQuery(Purchase.class);
       Root<Purchase> root = query.from(Purchase.class);
       query.select(root);


       Course course = session.get(Course.class,1);

       System.out.println(course.getName());





//       for (Student student : studentList){



 //      }






//        session.beginTransaction();
//        for (Purchase purchase : purchaseList){
//
//            courseName = purchase.getCourseName();
//           studentName = purchase.getStudentName();
//
//             List<Course> courseList = session.createQuery(hqlCourse).getResultList();
//              courseList.stream().map(course1 -> course1.getId()).forEach(System.out::println);
//            System.out.println("****************************************************");
//             List<Student> studentsList = session.createQuery(hqlStudent).getResultList();
//              studentsList.stream().map(student1 -> student1.getId()).forEach(System.out::println);
//             Query query = session.createSQLQuery("INSERT INTO linked_purchase " +
//                     "(" + ",firstname,lastname) \n" +
//                     "VALUES \n" +
//                     "('3','Ivan','Ivanov');");
//        }
//


        sessionFactory.close();

    }
}
//    Земцовский Всеволод
//    Грузинский Парфен
//    Калашников Терентий
//    Молодых Елисей
//    Кутепов Ефим
//    Погребняк Давид
//    Щеглов Ким
//    Толмачёв Аристарх
//    Саввин Онисим
//    Бойков Максим
//    Бурцов Никита
//    Квасников Емельян
//    Кандаков Адриан
//    Дьячков Севастьян
