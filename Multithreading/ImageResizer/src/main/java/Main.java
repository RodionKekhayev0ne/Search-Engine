import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static int newSize = 300;

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\Admin\\Desktop\\src";
        String dstFolder = "C:\\Users\\Admin\\Desktop\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int length = files.length / 4;


        File[] thread1 = new File[length];
        System.arraycopy(files,0,thread1,0,thread1.length);
        ImageResizer resizer1 = new ImageResizer(thread1,dstFolder,newSize);
        resizer1.start();

        File[] thread2 = new File[length];
        System.arraycopy(files,length,thread2,0,thread2.length);
        ImageResizer resizer2 = new ImageResizer(thread2,dstFolder,newSize);
        resizer2.start();

        File[] thread3 = new File[length];
        System.arraycopy(files,(length * 2),thread3,0,thread3.length);
        ImageResizer resizer3 = new ImageResizer(thread3,dstFolder,newSize);
        resizer3.start();

        File[] thread4 = new File[length];
        System.arraycopy(files,(length * 3),thread4,0,thread4.length);
        ImageResizer resizer4 = new ImageResizer(thread4,dstFolder,newSize);
        resizer4.start();



    }
}
