package ai.hyperlearning.pob.core.utils;

/**
 * JSON Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class JsonUtils {
    
    private JsonUtils() {
        throw new IllegalStateException("The JsonUtils utility class "
                + "cannot be instantiated.");
    }
    
    /**
     * Clean a given JSON value string
     * @param valueString
     * @return
     */
    
    public static String cleanValueString(String valueString) {
        return valueString
                .replace("\"", "'")
                .replace("\\", "/")
                .strip();
    }

}
