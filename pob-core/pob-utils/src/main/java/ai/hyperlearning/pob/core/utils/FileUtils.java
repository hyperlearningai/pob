package ai.hyperlearning.pob.core.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class FileUtils {
    
    private FileUtils() {
        throw new IllegalStateException("The FileUtils utility class "
                + "cannot be instantiated.");
    }
    
    /**
     * Determine whether a file exists at a given file path
     * @param filePath
     * @return
     */
    
    public static boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

}
