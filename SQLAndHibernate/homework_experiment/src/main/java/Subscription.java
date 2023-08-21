import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "Subscriptions")
public class Subscription {

        @EmbeddedId
        private Key id;

//        @ManyToOne
//        @JoinColumn(name="student_id", nullable=false)



        @Column(name = "student_id", insertable = false, updatable = false)
        private int studentId;

        @Column(name = "course_id", insertable = false, updatable = false)
        private int courseId;


        public Date getSubscriptionDate() {
                return subscriptionDate;
        }

        @Column(name = "subscription_date", insertable = false, updatable = false)
        private Date subscriptionDate;


        public int getStudentId() {
                return studentId;
        }

        public int getCourseId() {
                return courseId;
        }
    }

