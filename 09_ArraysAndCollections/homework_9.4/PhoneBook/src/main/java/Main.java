import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static final String YES_COMMAND = "Y";
    public static final String NO_COMMAND = "N";


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();


        String data = "";
        data += input + " ";

        doCommand(data);

    }

    public static void doCommand(String inputData) {

        String[] information = inputData.split("\\s+");
        PhoneBook phoneBook = new PhoneBook();


        if (information.length == 2) {
            if (Character.isDigit(information[0].charAt(0)) && Character.isLetter(information[1].charAt(0))) {

                if (phoneBook.getNameByPhone(information[0]).isEmpty()) {
                    phoneBook.addContact(information[0], information[1]);
                } else {
                    System.out.println(phoneBook.getNameByPhone(information[0]));
                }


            } else if (Character.isDigit(information[1].charAt(0)) && Character.isLetter(information[0].charAt(0))) {

                if (phoneBook.getPhonesByName(information[0]).size() == 0) {
                    phoneBook.addContact(information[1], information[0]);
                } else {
                    System.out.println(phoneBook.getPhonesByName(information[1]));

                }

                if (Character.isDigit(information[0].charAt(0)) && Character.isDigit(information[1].charAt(0))) {
                    return;
                }
                if (Character.isLetter(information[0].charAt(0)) && Character.isLetter(information[1].charAt(0))) {
                    return;
                }

            }
        }
    }
}