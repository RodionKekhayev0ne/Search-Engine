public class Main {

    public static void main(String[] args) {
        String line = "hhh, ggg, fff, eee, ddd, ccc, bb, aa, 7, 6, 5, 4, 3, 2, 1";
        String[] lineText = line.split(",?\\s+");

        System.out.println(ReverseArray.reverse(lineText));


    }

}
