package ai.hyperlearning.pob.data.publishers;

/**
 * Common Publisher Properties
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class CommonPublisherProperties {
    
    private CommonPublisherProperties() {
        throw new IllegalStateException("The CommonPublisherProperties "
                + "utility class cannot be instantiated.");
    }
    
    // Common message content and formatting
    public static final int MESSAGE_DASH_CHARACTER_LENGTH = 30;
    public static final String POB_ICON = 
            "https://avatars.slack-edge.com/2021-07-19/2282480381718_"
            + "db5803e467659a04f9c5_512.png";
    public static final String UNKNOWN_TEXT_VALUE = "Unknown";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_TITLE = 
            "[OPPORTUNITY_TITLE]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_BUYER = 
            "[OPPORTUNITY_BUYER]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME = 
            "[OPPORTUNITY_FRAMEWORK_NAME]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED = 
            "[OPPORTUNITY_DATE_PUBLISHED]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING = 
            "[OPPORTUNITY_DATE_CLOSING]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY = 
            "[OPPORTUNITY_SUMMARY]";
    public static final String JSON_PLACEHOLDER_OPPORTUNITY_URL = 
            "[OPPORTUNITY_URL]";
    public static final String MESSAGE_CONTENT_TEMPLATE = 
            "-".repeat(MESSAGE_DASH_CHARACTER_LENGTH) + "\\n" + 
            "*New Opportunity: " + JSON_PLACEHOLDER_OPPORTUNITY_TITLE + "*\\n" +
            "-".repeat(MESSAGE_DASH_CHARACTER_LENGTH) + "\\n" + 
            "*Buyer:* " + JSON_PLACEHOLDER_OPPORTUNITY_BUYER + "\\n" + 
            "*Framework:* " + JSON_PLACEHOLDER_OPPORTUNITY_FRAMEWORK_NAME + "\\n" + 
            "*Date Published:* " + JSON_PLACEHOLDER_OPPORTUNITY_DATE_PUBLISHED + "\\n" + 
            "*Date Closing:* " + JSON_PLACEHOLDER_OPPORTUNITY_DATE_CLOSING + "\\n" + 
            "*Summary:* " + JSON_PLACEHOLDER_OPPORTUNITY_SUMMARY + "\\n" + 
            "*Link:* " + JSON_PLACEHOLDER_OPPORTUNITY_URL + "\\n\\n" + 
            "↓"; 

}
