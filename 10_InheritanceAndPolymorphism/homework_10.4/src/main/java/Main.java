public class Main {
    public static void main(String[] args) {


        long a = System.currentTimeMillis();
        Company company = new Company();
        company.hireAll(80, 10, 180);
        company.showEmployees();
        System.out.println(company.getTopSalaryStaff(15));
        System.out.println(company.getLowestSalaryStaff(30));
        company.fire(135);
        company.showEmployees();
        System.out.println(company.getTopSalaryStaff(15));
        System.out.println(company.getLowestSalaryStaff(30));


        System.out.println(company.getIncome());
        long b = System.currentTimeMillis();

        System.out.println(b - a);
    }
}
