package ai.hyperlearning.pob.data.parsers.exceptions;

/**
 * Framework Parsing Exception
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class FrameworkParsingException extends RuntimeException {

    private static final long serialVersionUID = -1176356537299095163L;
    
    public FrameworkParsingException() {
        super("An error was encountered when attempting to "
                + "parse a framework.");
    }
    
    public FrameworkParsingException(String message) {
        super(message);
    }
    
    public FrameworkParsingException(String message, String exception) {
        super(message + " " + exception);
    }

}
