public class IndividualBusinessman extends Client {

    private double amount;

    @Override
    public void put(double amountToPut) {
        if (amountToPut < 0) {
            return;
        }
        double percent = 0;
        if (amountToPut < 1000) {
            percent = amountToPut / 100;
            amount += (amountToPut - percent);
        } else if (amountToPut >= 1000) {
            percent = amountToPut / 200;
            amount += (amountToPut - percent);
        }
    }

    @Override
    public void take(double amountToTake) {
        if (amountToTake > amount || amountToTake < 0) {
            return;
        }

        amount = amount - amountToTake;
    }


    public double getAmount() { return amount; }

}
