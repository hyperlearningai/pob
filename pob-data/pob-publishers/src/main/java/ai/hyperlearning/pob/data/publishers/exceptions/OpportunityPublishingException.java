package ai.hyperlearning.pob.data.publishers.exceptions;

/**
 * Opportunity Publishing Exception
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class OpportunityPublishingException extends RuntimeException {

    private static final long serialVersionUID = -6403872263403217684L;
    
    public OpportunityPublishingException() {
        super("An error was encountered when attempting to "
                + "publish an opportunity.");
    }
    
    public OpportunityPublishingException(String message) {
        super(message);
    }
    
    public OpportunityPublishingException(String message, String exception) {
        super(message + " " + exception);
    }

}
