import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailList {

    private final String regex = "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";

    ArrayList<String> emails = new ArrayList<>();

    public void add(String email) {
        // TODO: валидный формат email добавляется

        if (emails.contains(email.toLowerCase(Locale.ROOT))) {
            return;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {

            emails.add(email.toLowerCase(Locale.ROOT));
            Collections.sort(emails);

        } else {
            System.out.println("Неверный формат email");
        }

    }

    public List<String> getSortedEmails() {
        // TODO: возвращается список электронных адресов в алфавитном порядке
        return new ArrayList<>(emails);
    }

}
