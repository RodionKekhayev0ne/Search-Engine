import java.util.*;

public class Company {

//  indexes ->    M - MANAGER   *|*   T - TOPMANAGER   *|*   O - OPERATOR

    private double income = Math.round(115000 + (25000 * Math.random()));

    private List<Employee> employeeList = new ArrayList<>();


    public void hire(String index) {

        if (index.equals("M")) {
            employeeList.add(getNewEmployee("M"));
        }
        if (index.equals("T")) {
            employeeList.add(getNewEmployee("T"));
        }
        if (index.equals("O")) {
            employeeList.add(getNewEmployee("O"));
        }
    }

    public void hireAll(int managerCount, int topManagerCount, int operatorCount) {


        for (int i = 0; i < managerCount; i++) {
            employeeList.add(getNewEmployee("M"));
        }

        for (int i = 0; i < topManagerCount; i++) {
            employeeList.add(getNewEmployee("T"));
        }

        for (int i = 0; i < operatorCount; i++) {
            employeeList.add(getNewEmployee("O"));
        }

    }

    public void fireById(int id) {


        employeeList.remove(id);
    }


    public void fire(int count) {
        if (employeeList.size() < count) {
            return;
        }

        for (int i = count; i > 0; --i) {
            employeeList.remove(i);
        }
    }

    public void showEmployees() {
        for (Employee employees : employeeList) {
            System.out.println(employees);
        }
        System.out.println(employeeList.size());
    }

    public List<Employee> getTopSalaryStaff(int count) {
        if (employeeList.size() < count || count < 0) {
            return new ArrayList<>();
        }

        List<Employee> highestSalary = new ArrayList<>();
        Collections.sort(employeeList, Comparator.comparing(Employee::getMonthSalary));
        Collections.reverse(employeeList);
        for (int i = 0; i < count; i++) {
            highestSalary.add(employeeList.get(i));
        }

        return highestSalary;
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        if (employeeList.size() < count || count < 0) {
            return new ArrayList<>();
        }
        List<Employee> lowestSalary = new ArrayList<>();
        Collections.sort(employeeList, Comparator.comparing(Employee::getMonthSalary));
        for (int i = 0; i < count; i++) {
            lowestSalary.add(employeeList.get(i));
        }
        return lowestSalary;
    }

    public double getIncome() {
        return income;
    }


    public void setIncome(double income) {
        this.income += income;
    }

    private Employee getNewEmployee(String index) {

        if (index.equals("M")) {
            Employee manager = new Manager();
            return manager;
        } else if (index.equals("T")) {
            Employee topManager = new TopManager(income);
            return topManager;
        } else if (index.equals("O")) {
            Employee operator = new Operator();
            return operator;
        }
        return new Manager();
    }


}































