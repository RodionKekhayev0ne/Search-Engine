import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Movements {



    private String[] splitCalculations;

    private HashMap<String, Double> listOfIncome = new HashMap<>();
    private HashMap<String, Double> listOfPayment = new HashMap<>();

    private HashMap<String, String> companiesPayment = new HashMap<>();


    public Movements(String pathMovementsCsv) {
        try {


            List lines = Files.readAllLines(Paths.get(pathMovementsCsv));
            lines.remove(0);
            lines.forEach(l -> {
                String[] fragments = l.toString().split(",", 7);
                if (fragments.length != 7) {
                    System.out.println("Wrong line: " + l);
                } else {
                    String purpose = fragments[5].replaceAll("\\\\", "\\/").trim();
                    String[] split = purpose.split("/", 2);
                    String[] split2 = split[1].split("[0-9]{2}" + "[.]", 2);
                    String splitPurpose = split2[0];

                    String calculations = fragments[6];

                    companiesPayment.put(splitPurpose, split2[1]);


                    if (!calculations.contains("\",")) {
                        splitCalculations = calculations.split(",", 2);
                    } else {
                        splitCalculations = calculations.split("\",", 2);
                    }

                    double columnIncome = Double.parseDouble(splitCalculations[0].replaceAll("\"", "").replace(',', '.'));
                    double columnPayment = Double.parseDouble(splitCalculations[1].replaceAll("\"", "").replace(',', '.'));


                    if (!listOfIncome.containsKey(splitPurpose)) {
                        listOfIncome.put(splitPurpose, columnIncome);
                    } else {
                        double sum = listOfIncome.get(splitPurpose);
                        sum += columnIncome;
                        listOfIncome.put(splitPurpose, sum);
                    }

                    if (!listOfPayment.containsKey(splitPurpose)) {
                        listOfPayment.put(splitPurpose, columnPayment);
                    } else {
                        double sum = listOfPayment.get(splitPurpose);
                        sum += columnPayment;

                        listOfPayment.put(splitPurpose, sum);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    Тип счёта
//Номер счета
//Валюта
//Дата операции
//Референс проводки
//Описание операции
//Приход
//Расход 4

    public double getExpenseSum() {
        double sum = 0;
        for (String key : listOfPayment.keySet()) {

            sum += listOfPayment.get(key);
        }
        return sum;
    }


    public double getIncomeSum() {
        double sum = 0;

        for (String key : listOfIncome.keySet()) {

            sum += listOfIncome.get(key);

        }
        return sum;
    }

    public String getPaymentByCompanies() {

        String result = "";
        double sum = 0;


        for (String key : companiesPayment.keySet()) {

            String[] keySplit = key.split("/", 4);

            for (String key2 : companiesPayment.keySet()) {

                String[] key2Split = key2.split("/", 4);
                String[] splitMoney = companiesPayment.get(key2).split("\\s+");


                if (keySplit[0].equals(key2Split[0])) {
                    sum += Double.parseDouble(splitMoney[2]);
                }

            }

            int index = keySplit.length;
            result += keySplit[keySplit.length - 1] + " - " + sum + "\n";
            sum = 0;
        }
        return result;
    }


}
