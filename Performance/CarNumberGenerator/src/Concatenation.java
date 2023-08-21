
public class Concatenation {
// 1299ms   18ms
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String str = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20_000; i++) {
            builder.append("some text some text some text");

            if (builder.toString().length() > 2000){
                builder = new StringBuilder();
            }
        }



        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
