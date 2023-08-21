public abstract class Client {

    private double amount;

    public double getAmount() {

        return amount;
    }

    public  void put(double amountToPut){
        if (amountToPut < 0) {
            return;
        }

        amount += amountToPut;
    }

    public void take(double amountToTake) {
        if (amountToTake > amount || amountToTake < 0) {
            return;
        }

        amount = amount - amountToTake;
    }

}
