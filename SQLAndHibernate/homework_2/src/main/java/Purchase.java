import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "purchaseList")
public class Purchase {
    @EmbeddedId
    private PurchaseKey key;
    @Column(name = "student_name", insertable = false, updatable = false)
    private String studentName;
    @Column(name = "course_name", insertable = false, updatable = false)
    private String courseName;
    private int price;
    @Column(name = "subscription_date", insertable = false, updatable = false)
    private Date subscriptionDate;

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getPrice() {
        return price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }
}