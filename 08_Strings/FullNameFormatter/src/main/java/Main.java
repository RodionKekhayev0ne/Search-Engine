import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            input = input.trim();
            if (wordCounter(input) == 2 && isCyrillic(input)) {
                System.out.println("Фамилия: " + input.substring(0, input.indexOf(' ')).trim());
                System.out.println("Имя: " + input.substring(input.indexOf(' ') + 1, input.lastIndexOf(' ')).trim());
                System.out.println("Отчество: " + input.substring(input.lastIndexOf(' ')).trim());
            } else {
                System.out.println("Введенная строка не является ФИО");
            }
        }
    }

    private static int wordCounter(String text) {
        int wordCounter = 0;
        while (text.length() > 0) {
            if (text.indexOf(' ') > 0) {
                text = text.substring(text.indexOf(' ') + 1).trim();
                wordCounter++;
            } else {
                text = "";
            }
        }
        return wordCounter;
    }

    private static boolean isCyrillic(String text) {
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) < 'А' || text.charAt(i) > 'Я') && text.charAt(i) != 'Ё'
                    && text.charAt(i) != ' ' && text.charAt(i) != '-') {
                return false;
            }
        }
        return true;
    }


}
