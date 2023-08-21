

import javax.persistence.*;

import java.util.Date;


@Entity
@Table(name = "linkedpurchaselist")
public class LinkedPurchaseList{

    @EmbeddedId
    private Key id;

    public LinkedPurchaseList(Key key, int studentId, int courseId, Date subscriptionDate, int price) {
        id = key;
        this.studentId = studentId;
        this.courseId = courseId;
        this.subscriptionDate = subscriptionDate;
        this.price = price;
    }

    @Column(name = "student_id", insertable = false, updatable = false)
    private int studentId;

    @Column(name = "course_id", insertable = false, updatable = false)
    private int courseId;

    protected LinkedPurchaseList() {
        super();
    }


    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    @Column(name = "subscription_date", insertable = false, updatable = false)
    private Date subscriptionDate;


    private int price;

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getPrice() {
        return price;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
