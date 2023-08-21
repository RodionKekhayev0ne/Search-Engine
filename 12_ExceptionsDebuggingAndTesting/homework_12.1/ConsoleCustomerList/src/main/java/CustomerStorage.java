import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;


        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalStateException();
        }
        checkEmail(components[INDEX_EMAIL]);
        checkNumber(components[INDEX_PHONE]);
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));


    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }

    private void checkNumber(String number) {
        String regex = "[^0-9]";
        String regex2 = "[7,8][0-9]{10}";
        number = number.replaceAll(regex, "");

        for (int i = 0; i < number.length(); i++) {
            if (Character.isLetter(number.charAt(i))) {
                throw new IllegalStateException();
            }
        }
        if (number.length() != 11 || !number.matches(regex2)) {
            throw new IllegalStateException();
        }
    }

    private void checkEmail(String email) {
        String regex = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
    }
}