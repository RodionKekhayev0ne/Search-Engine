
public class Main {
    public static void main(String[] args) {

        String path = "FilesAndNetwork/files/movementList.csv";

        Movements movements = new Movements(path);

        System.out.println(movements.getExpenseSum());
        System.out.println(movements.getIncomeSum());

        System.out.println(movements.getPaymentByCompanies());


    }
}
