public class LegalPerson extends Client {


    private double amount;

    @Override
    public void put(double amountToPut) {
        if (amountToPut < 0) {
            return;
        }

        amount += amountToPut;
    }

    @Override
    public void take(double amountToTake) {

        if (amountToTake > amount || amountToTake < 0) {
            return;
        }
        double percent = amountToTake / 100.0;
        amount = amount - (amountToTake + percent);
    }

    public double getAmount() { return amount; }
}
