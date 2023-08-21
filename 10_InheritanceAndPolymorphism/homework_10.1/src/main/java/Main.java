public class Main {
    public static void main(String[] args) {

        DepositAccount depositAccount = new DepositAccount();

        depositAccount.put(65000);
        depositAccount.getAmount();
        depositAccount.take(20000);
//        depositAccount.rollBackTime(1,1,1);
        depositAccount.take(20000);
        depositAccount.getAmount();

    }
}
