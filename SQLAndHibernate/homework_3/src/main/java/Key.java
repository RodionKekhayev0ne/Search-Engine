import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Key implements Serializable {


    public Key(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }



    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;




    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return getStudentId() == key.getStudentId() && getCourseId() == key.getCourseId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getCourseId());
    }



}