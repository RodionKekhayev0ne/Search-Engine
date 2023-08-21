import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class DepositAccount extends BankAccount {

    private double amount;
    private LocalDate lastIncome;

    public DepositAccount() {

    }


    public void put(double amountToPut) {
        if (amountToPut < 0.0) {
            System.out.println("Недопустимая сумма");
            return;
        }
        amount = amount + amountToPut;
        System.out.println("счет пополнен на " + amountToPut + " rub");
        lastIncome = LocalDate.now();
    }

//    public void rollBackTime(int years, int months, int days) {
//
//
//        lastIncome.plusYears(years);
//        lastIncome.plusMonths(months);
//        lastIncome.plusDays(days);
//    }

    public void take(double amountToTake) {
        LocalDate now = LocalDate.now();

        long compare = Math.round(ChronoUnit.MONTHS.between(lastIncome, now));
        if (compare < 1) {
            System.out.println("С последнего пополнения не прошло месяца. Bы не можете снять деньги");
            System.out.println("Последнее пополнение" + " " + lastIncome);
        } else {


            if (amountToTake > amount || amountToTake < 0.0) {
                System.out.println("Недопустимая сумма");

            } else {
                amount = amount - amountToTake;

                System.out.println("Ваш счет равен: " + amount);
            }

        }
    }

    public double getAmount() {
        return amount;
    }


}
