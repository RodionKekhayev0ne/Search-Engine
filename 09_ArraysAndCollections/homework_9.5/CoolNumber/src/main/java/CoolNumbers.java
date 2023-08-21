import java.util.*;

public class CoolNumbers {




    public static List<String> generateCoolNumbers() {

        String[] X = {"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"};

        List<String> coolNumbers = new ArrayList<>();

        String[] regions = new String[200];

        for (int x = 0; x < 12; x++) {

            for (int n = 0; n < 10; n++) {

                for (int n2 = 0; n2 < 10; n2++) {

                    for (int n3 = 0; n3 < 10; n3++) {

                        for (int r = 0; r < 200; r++) {

                            regions[r] = String.valueOf(r);

                            if (r < 10) {
                                regions[r] = "00" + r;
                            } else if (r < 100) {
                                regions[r] = "0" + r;
                            }

                            coolNumbers.add(X[x] + n + n2 + n3 + X[x] + X[x] + regions[r]);
                        }
                    }
                }
            }
        }

        return coolNumbers;

    }

    public static boolean bruteForceSearchInList(List<String> list, String number) {
        long startTime = System.nanoTime();
        if (list.contains(number)) {
            System.out.println("Поиск перебором: номер найден, поиск занял " + (System.nanoTime() - startTime) + "нс");
            return true;
        }
        return false;
    }

    public static boolean binarySearchInList(List<String> sortedList, String number) {

        Collections.sort(sortedList);

        int binSerch = Collections.binarySearch(sortedList, number);
        long startTime = System.nanoTime();
        if (binSerch == 0) {
            System.out.println("Бинарный поиск: номер найден, поиск занял " + (System.nanoTime() - startTime) + "нс");
            return true;
        }

        return false;
    }

    public static boolean searchInHashSet(HashSet<String> hashSet, String number) {
        long startTime = System.nanoTime();
        if (hashSet.contains(number)) {
            System.out.println("Поиск в HashSet: номер найден, поиск занял " + (System.nanoTime() - startTime) + "нс");
            return true;
        }


        return false;
    }

    public static boolean searchInTreeSet(TreeSet<String> treeSet, String number) {
        long startTime = System.nanoTime();
        if (treeSet.contains(number)) {
            System.out.println("Поиск в TreeSet: номер найден, поиск занял " + (System.nanoTime() - startTime) + "нс");
            return true;
        }
        return false;
    }

}
