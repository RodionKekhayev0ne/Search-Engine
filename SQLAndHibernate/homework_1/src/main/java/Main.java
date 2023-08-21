import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "Ognaviastralis06";
       try {
           Connection connection = DriverManager.getConnection(url, user, pass);
           Statement statement = connection.createStatement();

           String sqlQuery = "select Courses.name, " +
                   "count(subscription_date)/(select month(subscription_date) from subscriptions " +
                   "where year(subscription_date) = 2018 " +
                   "order by subscription_date desc " +
                   "limit 1) as avarage_purchase_amount_per_month " +
                   "from subscriptions " +
                   "JOIN Courses on subscriptions.course_id = courses.id " +
                   "group by course_id " +
                   "order by course_id";

           ResultSet resultSet = statement.executeQuery(sqlQuery);
//            statement.execute("update courses " +
//                    "set name ='Веб-разработчик c нуля до PRO' where id = 1");
           while (resultSet.next()) {
               System.out.println(resultSet.getString("name") + "\t" + resultSet.getString("avarage_purchase_amount_per_month"));
           }
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }
}
