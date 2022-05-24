package ai.hyperlearning.pob.core.utils;

/**
 * HTTP Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class HttpUtils {
    
    private HttpUtils() {
        throw new IllegalStateException("The HttpUtils utility class "
                + "cannot be instantiated.");
    }
    
    /**
     * Convert a given string of parameters to a GET query string
     * @param params
     * @return
     */
    
    public static String toGetQueryParams(String params) {
        return params.replace(" ", "%20").strip();
    }

}
