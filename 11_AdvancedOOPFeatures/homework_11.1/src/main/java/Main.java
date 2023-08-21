import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Main {

    public static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {


        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);



        System.out.println(staff);
    }

    public static void sortBySalaryAndAlphabet(List<Employee> staff) {
        //TODO Метод должен отсортировать сотрудников по заработной плате и алфавиту.

        Collections.sort(staff, (o1, o2) -> {
            int comSalary = 0;
            int comName = 0;

            if (o1.getSalary().compareTo(o2.getSalary()) != 0) {
                comSalary = o1.getSalary().compareTo(o2.getSalary());
            } else if (o1.getSalary().compareTo(o2.getSalary()) == 0) {
                comName = o1.getName().compareTo(o2.getName());
            }
            return comSalary + comName;
        });
    }
}



























