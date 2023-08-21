import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank extends Thread {

    private  Map<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();


    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {

        if (accounts.get(fromAccountNum).isBlock() && accounts.get(toAccountNum).isBlock()) {
            System.out.println("Accounts blocked!!!");
        } else if (amount >= 50000) {
            accounts.get(fromAccountNum).block();
            accounts.get(toAccountNum).block();
            System.out.println("Accounts will be block");
        }

        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {


        if (accounts.get(fromAccountNum).getMoney() < amount){
              System.out.println("Недостаточно средств");
        }else if (accounts.get(fromAccountNum).isBlock() || accounts.get(toAccountNum).isBlock()){

            System.out.println("Ваши акаунты заблокированны!!!");

        }else {

            synchronized (accounts) {

                Account sender = accounts.get(fromAccountNum);
                sender.setMoney(sender.getMoney() - amount);

                Account recipient = accounts.get(toAccountNum);
                recipient.setMoney(recipient.getMoney() + amount);

                System.out.println("SUCCESSFUL TRANSFER!!!");
                try {
                    if (isFraud(fromAccountNum, toAccountNum, amount)) {
                        sender.block();
                        recipient.block();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long  getBalance(String accountNum) {
        long amount = accounts.get(accountNum).getMoney();
        return amount;
    }

    public long getSumAllAccounts() {
        long totalSum = 0;
        synchronized (accounts) {
            for (String set : accounts.keySet()) {
                totalSum += accounts.get(set).getMoney();
            }
        }
        return totalSum;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public void setAccount(Account account) {
        synchronized (accounts){
            accounts.put(account.getAccNumber(), account);
        }
    }
}
