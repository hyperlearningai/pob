package ai.hyperlearning.pob.utils;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * Hash Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class HashUtils {

	public static String stringToHashSha512(String originalString) {
		return Hashing.sha512().hashString(
				originalString, StandardCharsets.UTF_8).toString();
	}
	
}
