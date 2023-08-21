import org.apache.commons.collections.Factory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;



import java.util.Date;
import java.util.List;



public class Main {

    public static SessionFactory sessionFactory ;
    public static Session session = getSession();

    public static void main(String[] args) {

        String hql = "From " + Purchase.class.getSimpleName();
        List<Purchase> purchaseList = getSession().createQuery(hql).getResultList();


        for(Purchase purchase : purchaseList) {



            String hqlSt = "From " + Student.class.getSimpleName() + " Where name = " + "'" + purchase.getStudentName() + "'";
            String hqlCr = "From " + Course.class.getSimpleName() + " Where name = " + "'" + purchase.getCourseName() + "'";

            List<Student> student = session.createQuery(hqlSt).getResultList();
            List<Course> course = session.createQuery(hqlCr).getResultList();

            int studentId = student.get(0).getId();
            int courseId = course.get(0).getId();


            session.getTransaction().begin();
            LinkedPurchaseList linkedPurchase = new LinkedPurchaseList(new Key(studentId,courseId),
                    studentId,
                    courseId,
                    purchase.getSubscriptionDate(),
                    purchase.getPrice());

            session.save(linkedPurchase);

            session.getTransaction().commit();
        }






        session.close();
        sessionFactory.close();
    }



    public static Session getSession(){

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        return session;
    }





}
