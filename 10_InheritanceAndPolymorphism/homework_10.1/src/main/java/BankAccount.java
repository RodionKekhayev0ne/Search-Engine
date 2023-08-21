public class BankAccount {


    private double amount;


    public double getAmount() {
        return amount;
    }

    public void put(double amountToPut) {
        if (amountToPut < 0.0) {
            System.out.println("Недопустимая сумма");
            return;
        }
        amount = amount + amountToPut;
        System.out.println("счет пополнен на " + amountToPut + " rub");
    }

    public void take(double amountToTake) {
        if (amountToTake > amount || amountToTake < 0.0) {
            System.out.println("Недопустимая сумма");
            return;
        }
        amount = amount - amountToTake;
        System.out.println("сo счета снято " + amountToTake + " rub");

    }
}
