public class CardAccount extends BankAccount {
    // не забывайте, обращаться к методам и конструкторам родителя
    // необходимо используя super, например, super.put(10D);


    private double amount;

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
        double comision = amountToTake / 100.0;
        amount = amount - (amountToTake + comision);
        System.out.println("сo счета снято " + (amountToTake + comision) + " rub" + " комиссия 1%");
        System.out.println(amount);

    }

    public double getAmount() {
        return amount;
    }
}
