public class TopManager implements Employee {

    private int salary = (int) Math.round(80000 + (20000 * Math.random()));


    public TopManager(double income) {

        if (income > 10000000) {
            salary += (salary / 100) * 150;
        }

    }

    @Override
    public String toString() {
        return "Top-manager" + " - " + salary + "p";
    }


    @Override
    public int getMonthSalary() {
        return salary;
    }


}
