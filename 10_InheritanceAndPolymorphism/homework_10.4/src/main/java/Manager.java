import java.util.TreeSet;

public class Manager implements Employee {


//       min salary 40000p          max salary 57000p

    private int salary = (int) Math.round(40000 + (10000 * Math.random()));
    private double bonus;

    public Manager() {
    }

    public Manager(double income) {

        bonus = (income / 100) * 5;
        salary += bonus;
    }

    public String toString() {
        return "Manager" + " - " + salary + "p";
    }


    public int getMonthSalary() {
        return salary;
    }

}
