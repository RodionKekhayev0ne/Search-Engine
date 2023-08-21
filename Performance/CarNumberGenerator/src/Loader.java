import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {


   // 18389ms 1589ms 970ms


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        FileOutputStream writer = new FileOutputStream("C:/Users/Admin/Desktop/java_basics/Performance/CarNumberGenerator/res/numbers.txt");

        char lettersFPart[] = {'У', 'К', 'Е'};
        char lettersSPart[] = {'А', 'Р', 'О' };
        char lettersTPart[] = {'Н', 'Х', 'В'};
        char lettersFourPart[] = {'С', 'М', 'Т'};

         CarNumCreator carNumCreator1 = new CarNumCreator(lettersFPart, writer);
         CarNumCreator carNumCreator2 = new CarNumCreator(lettersSPart, writer);
         CarNumCreator carNumCreator3 = new CarNumCreator(lettersTPart, writer);
         CarNumCreator carNumCreator4 = new CarNumCreator(lettersFourPart, writer);

        carNumCreator1.run();
        carNumCreator2.run();
        carNumCreator3.run();
        carNumCreator4.run();

        writer.flush();
        writer.close();

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

//    private static String padNumber(int number, int numberLength) {
//
//        builder.append(Integer.toString(number));
//
//        int padSize = numberLength - Integer.toString(number).length();
//        if (padSize != 0) {
//            for (int i = 0; i < padSize; i++) {
//                builder = new StringBuilder();
//                builder.append('0' + Integer.toString(number));
//            }
//        }
//        return builder.toString();
//    }

}
