import java.util.*;

public class PhoneBook {

    //77783049781 rodion

    Map<String, String> phoneBook = new TreeMap<>();
    Set<String> names = new TreeSet<>();
    Set<String> contacts = new TreeSet<>();

    public void addContact(String phone, String name) {
        // проверьте корректность формата имени и телефона
        // если такой номер уже есть в списке, то перезаписать имя абонента

        if (phoneBook.containsKey(phone)) {

            phoneBook.replace(phone, phoneBook.get(phone), name);


        } else {
            phoneBook.put(phone, name);
        }

    }

    public String getNameByPhone(String phone) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найдены - вернуть пустую строку
        if (phoneBook.containsKey(phone)) {
            return phoneBook.get(phone) + " - " + phone;
        }

        return "";
    }

    //TODO: Здесь
    public Set<String> getPhonesByName(String name) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet

        for (String key : phoneBook.keySet()) {
            if (phoneBook.get(key).equals(name)) {

                names.add(phoneBook.get(key) + " - " + key);
                return names;
            }
        }


        return new TreeSet<>();
    }

    //TODO: Здесь
    public Set<String> getAllContacts() {
        // формат одного контакта "Имя - Телефон"
        // если контактов нет в телефонной книге - вернуть пустой TreeSet


        for (String key : phoneBook.keySet()) {

            if (key.isEmpty() || phoneBook.get(key).isEmpty()) {
                return new TreeSet<>();
            }

            if (key.equals(phoneBook.get(key))) {
                return new TreeSet<>();
            } else {
                contacts.add(phoneBook.get(key) + " - " + key);
            }
        }


        return new TreeSet<>(contacts);
    }
}
