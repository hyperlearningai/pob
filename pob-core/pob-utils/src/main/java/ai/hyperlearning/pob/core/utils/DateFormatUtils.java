package ai.hyperlearning.pob.core.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Date Format Utility Methods
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class DateFormatUtils {
    
    private DateFormatUtils() {
        throw new IllegalStateException("The DateFormatUtils utility class "
                + "cannot be instantiated.");
    }
    
    private static final DateTimeFormatter EEEE_DD_MMMM_YYYY = DateTimeFormatter
            .ofPattern("EEEE dd MMMM yyyy");
    private static final DateTimeFormatter EEEE_D_MMMM_YYYY = DateTimeFormatter
            .ofPattern("EEEE d MMMM yyyy");
    private static final DateTimeFormatter DD_MMMM_YYYY = DateTimeFormatter
            .ofPattern("dd MMMM yyyy");
    private static final DateTimeFormatter D_MMMM_YYYY = DateTimeFormatter
            .ofPattern("d MMMM yyyy");
    
    /**
     * Get the current date as a string in the given date format
     * @param dateFormat
     * @return
     */
    
    public static String getCurrentDateString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }
    
    /**
     * Convert a given date string of the format "EEEE dd MMMM yyyy"
     * into a LocalDate object
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
    
    /**
     * Convert a given date string of the format "dd MMMM yyyy" 
     * into a LocalDate object
     * @param dateString
     * @return
     */
    
    public static LocalDate ddMMMMyyyyToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DD_MMMM_YYYY);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(dateString, D_MMMM_YYYY);
            } catch (DateTimeParseException e2) {
                return null;
            }
        }
    }

}
