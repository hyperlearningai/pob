package ai.hyperlearning.pob.data.parsers;

import java.util.Set;

import ai.hyperlearning.pob.data.parsers.exceptions.FrameworkParsingException;
import ai.hyperlearning.pob.model.Framework;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * Procurement Framework Parser
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public abstract class FrameworkParser {
    
    private Framework framework;
    
    protected FrameworkParser(Framework framework) {
        this.framework = framework;
    }
    
    public Framework getFramework() {
        return framework;
    }
    
    public abstract Set<Opportunity> parse() 
            throws FrameworkParsingException;

}
