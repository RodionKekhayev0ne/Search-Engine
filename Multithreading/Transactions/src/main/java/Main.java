import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        Bank bank2 = new Bank();
        Bank bank3 = new Bank();

        Account account = new Account();
        account.setMoney(200000);
        account.setAccNumber("1");

        Account account2 = new Account();
        account2.setMoney(10000);
        account2.setAccNumber("2");

        bank.setAccount(account);
        bank.setAccount(account2);
        bank2.setAccount(account);
        bank2.setAccount(account2);
        bank3.setAccount(account);
        bank3.setAccount(account2);


        bank.start();
        bank2.start();
        bank3.start();

         account.activate();
        bank.transfer(account.getAccNumber(), account2.getAccNumber(), 50000);
        System.out.println("Общая сумма в Банке 1 - " + bank.getSumAllAccounts());


        bank2.transfer(account.getAccNumber(), account2.getAccNumber(), 50000);
        System.out.println("Общая сумма в Банке 2 - " + bank2.getSumAllAccounts());

        bank3.transfer(account.getAccNumber(), account2.getAccNumber(), 50000);
        System.out.println("Общая сумма в Банке 3 - " + bank2.getSumAllAccounts());

    }


}
