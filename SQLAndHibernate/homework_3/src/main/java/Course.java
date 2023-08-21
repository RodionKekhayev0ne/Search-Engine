import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Courses")
public class Course {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private  CourseType type;




    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @Column(name = "students_count")
    private int studentsCount;

    private int price;

    @Column(name = "price_per_hour")
    private float pricePh;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Subscriptions",
        joinColumns = {@JoinColumn(name = "course_id")},
    inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<Student> studentsList;


    public String getName() {
        return name;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public List<Student> getStudents() {
        return studentsList;
    }

    public Teacher getTeacher() { return teacher; }

    public int getId() {
        return id;
    }
}
