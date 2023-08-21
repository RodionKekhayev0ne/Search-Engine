import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {

        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        Employee employeeMaxSalary = findEmployeeWithHighestSalary(staff, 2017);
        System.out.println(employeeMaxSalary);


    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {
        //TODO Метод должен вернуть сотрудника с максимальной зарплатой среди тех,
        // кто пришёл в году, указанном в переменной year

        int index = 1;
        String dateString = "01/01/" + year;
        String dateAfterString = "01/01/" + (++year);

        SimpleDateFormat timeForm = new SimpleDateFormat("dd/MM/yyyy");

        ParsePosition parsePosition = new ParsePosition(index);
        ParsePosition parsePosition2 = new ParsePosition(index);

        Date compareDate = timeForm.parse(dateString, parsePosition);
        Date compareAfterDate = timeForm.parse(dateAfterString, parsePosition2);

        Optional<Employee> emp = staff.stream()
                .filter(employee -> employee.getWorkStart().after(compareDate))
                .filter(employee -> employee.getWorkStart().before(compareAfterDate))
                .max(Comparator.comparing(Employee::getSalary));
        Employee filteredEmploye = emp.get();

        return filteredEmploye;

    }
}