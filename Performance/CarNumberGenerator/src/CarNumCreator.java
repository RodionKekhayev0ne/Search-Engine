import java.io.FileOutputStream;

public class CarNumCreator extends Thread{

    static StringBuilder builder = new StringBuilder();
    private char[] letters;
    public  FileOutputStream writer;


    CarNumCreator(char[] lettersArr, FileOutputStream writer){
        letters = lettersArr;
        this.writer = writer;
    }


    @Override
    public void run() {
        try {
            for (int number = 1; number < 1000; number++) {
                int regionCode = 199;
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
                            writer.write(builder.toString().getBytes());
                        }
                    }
                }
                    builder = new StringBuilder();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private  String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();

        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }

        return numberStr;
    }
}
