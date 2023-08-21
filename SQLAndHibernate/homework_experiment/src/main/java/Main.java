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

    public static Session sesion = getSesion()  ;
    public static  SessionFactory sessionFactory;

    public static void main(String[] args) {

        Date date = new Date();

       sesion.getTransaction().begin();

       LinkedPurchaseList linkedPurchase = new LinkedPurchaseList(new Key(1,1),1,1,date,111);

       sesion.save(linkedPurchase);

       sesion.getTransaction().commit();

       sesion.close();
       sessionFactory.close();

    }



    public static Session getSesion(){

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        return session;
    }



    public static void addLinkedPurchase(Key id, int studentId, int courseId, Date subscriptionDate, int price){

    }
}
