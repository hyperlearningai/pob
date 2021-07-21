package ai.hyperlearning.pob.utils;

/**
 * String Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class StringUtils {
	
	public static String keywordsToGetRequestParams(String keywords) {
		return keywords.replace(" ", "%20").strip();
	}
	
	public static String cleanJsonValueString(String value) {
		return value
				.replace("\"", "'")
				.replace("\\", "/")
				.strip();
	}

}
