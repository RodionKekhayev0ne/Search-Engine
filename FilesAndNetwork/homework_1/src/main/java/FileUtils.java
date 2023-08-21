import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//C:/Users/Admin/Desktop
public class FileUtils {
    //5643Mb 5917829104 1048576
    public static long calculateFolderSize(String path) {
        try {

            Path folder = Paths.get(path);
            return Files.walk(folder).map(Path::toFile).filter(File::isFile).mapToLong(File::length).sum();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
