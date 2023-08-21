public class Operator implements Employee {

    private int salary = 35000;


    public Operator() {

    }

    @Override
    public String toString() {
        return "Operator" + " - " + salary + "p";
    }


    @Override
    public int getMonthSalary() {
        return salary;
    }


}
