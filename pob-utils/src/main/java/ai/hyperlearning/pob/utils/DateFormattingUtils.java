package ai.hyperlearning.pob.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Date Formatting Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class DateFormattingUtils {
	
	private static final DateTimeFormatter EEEE_DD_MMMM_YYYY = DateTimeFormatter
			.ofPattern("EEEE dd MMMM yyyy");
	private static final DateTimeFormatter EEEE_D_MMMM_YYYY = DateTimeFormatter
			.ofPattern("EEEE d MMMM yyyy");
	
	/**
	 * Parse string date of format EEEE dd MMMM yyyy
	 * @param dateString
	 * @return
	 */
	
	public static LocalDate EEEEddMMMMyyyyToLocalDate(String dateString) {
		try {
			return LocalDate.parse(dateString, EEEE_DD_MMMM_YYYY);
		} catch (DateTimeParseException e1) {
			try {
				return LocalDate.parse(dateString, EEEE_D_MMMM_YYYY);
			} catch (DateTimeParseException e2) {
				return null;
			}
		}
	}

}
