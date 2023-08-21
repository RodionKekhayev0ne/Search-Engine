import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseKey implements Serializable {


    public PurchaseKey(String courseName) {
        this.courseName = courseName;
    }



    public PurchaseKey(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }




    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseKey that = (PurchaseKey) o;
        return Objects.equals(getStudentName(), that.getStudentName()) && Objects.equals(getCourseName(), that.getCourseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentName(), getCourseName());
    }

}