package ai.hyperlearning.pob.core.utils;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * Hashing Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class HashUtils {
    
    private HashUtils() {
        throw new IllegalStateException("The HashUtils utility class "
                + "cannot be instantiated.");
    }
    
    /**
     * Hash a given string using SHA512
     * @param str
     * @return
     */
    
    public static String toHashSha512(String str) {
        return Hashing.sha512()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();
    }

}
