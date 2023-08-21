public class Main {

    public static void main(String[] args) {

        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        //TODO: напишите ваш код, результат вывести в консоль


        System.out.println(CalculateMoney(text));

    }

    public static int CalculateMoney(String text) {

        String end = "рублей";
        String endPetia = "рубля";

        String charset1 = "заработал";
        int start1 = text.indexOf(charset1) + charset1.length();
        int end1 = text.indexOf(end, start1);
        String vasiaMoney = text.substring(start1, end1);

        String charset2 = "Петя - ";
        int start2 = text.indexOf(charset2) + charset2.length();
        int end2 = text.indexOf(endPetia, start2);
        String petiaMoney = text.substring(start2, end2);

        String charset3 = "Маша - ";
        int start3 = text.indexOf(charset3) + charset3.length();
        int end3 = text.indexOf(end, start3);
        String mashaMoney = text.substring(start3, end3);


        return Integer.parseInt(vasiaMoney.trim()) + Integer.parseInt(petiaMoney.trim()) + Integer.parseInt(mashaMoney.trim());
    }

}