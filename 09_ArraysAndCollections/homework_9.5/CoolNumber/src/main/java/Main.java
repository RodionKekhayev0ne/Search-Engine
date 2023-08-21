import java.util.*;

public class Main {
    /*
    TODO:
     - реализовать методы класса CoolNumbers
     - посчитать время поиска введимого номера в консоль в каждой из структуры данных
     - проанализоровать полученные данные
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What number do you search ?");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }

            if (input.equals("ALL")){
                for (String x : CoolNumbers.generateCoolNumbers()){
                    System.out.println(x);
                }
            }

            List<String> coolNumber = new ArrayList<>(CoolNumbers.generateCoolNumbers());

            TreeSet coolTree = new TreeSet(coolNumber);
            HashSet coolHash = new HashSet(coolNumber);

            CoolNumbers.bruteForceSearchInList(coolNumber, input);
            CoolNumbers.binarySearchInList(coolNumber, input);
            CoolNumbers.searchInHashSet(coolHash, input);
            CoolNumbers.searchInTreeSet(coolTree, input);
        }
    }
}