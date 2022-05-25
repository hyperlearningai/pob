package ai.hyperlearning.pob.data.publishers.csv;

import java.io.FileWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;

import ai.hyperlearning.pob.core.utils.FileUtils;
import ai.hyperlearning.pob.data.publishers.OpportunityPublisher;
import ai.hyperlearning.pob.data.publishers.exceptions.OpportunityPublishingException;
import ai.hyperlearning.pob.model.Opportunity;

/**
 * CSV Publisher
 *
 * @author jillurquddus
 * @since 2.0.0
 */

public class CsvPublisher extends OpportunityPublisher {
    
    private static final Logger LOGGER = 
            LoggerFactory.getLogger(CsvPublisher.class);
    
    // CSV properties
    private static final String CSV_FILE_PATH_PROPERTY_KEY = "path";
    private static final String[] CSV_HEADER = {
            "ID", "URI", "URL", "TITLE", "BUYER", 
            "DATE_PUBLISHED", "DATE_CLOSING", "SUMMARY"
    };
    
    public CsvPublisher(Map<String, Object> properties) {
        super(properties);
    }
    
    public void publish(Opportunity opportunity) {
        
        // Check whether the file exists before creating the OpenCSV writer
        LOGGER.info("Started the CSV publisher.");
        String filePath = (String) getProperties()
                .get(CSV_FILE_PATH_PROPERTY_KEY);
        boolean fileExists = FileUtils.fileExists(filePath);
        
        try (
                FileWriter fileWriter = new FileWriter(filePath, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter,
                        ICSVWriter.DEFAULT_SEPARATOR,
                        ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                        ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        ICSVWriter.DEFAULT_LINE_END); ) {
                
                // Write the header record if required
                if (!fileExists)
                    csvWriter.writeNext(CSV_HEADER);
                
                // Convert the opportunity object into a String array.
                // We will NOT use the StatefulBeanToCsvBuilder as this writes
                // the header row before each append. See the following link:
                // https://stackoverflow.com/questions/48922642/appending-to-csv-file-without-headers
                csvWriter.writeNext(new String[]{
                        opportunity.getId(), 
                        opportunity.getUri(), 
                        opportunity.getUrl(), 
                        opportunity.getTitle(), 
                        opportunity.getBuyer(), 
                        opportunity.getDatePublished() != null ? 
                                opportunity.getDatePublished().toString() : "", 
                        opportunity.getDateClosing() != null ? 
                                opportunity.getDateClosing().toString() : "", 
                        opportunity.getSummary()});
            
        } catch (Exception e) {
            
            String message = "An error was encountered whilst attempting to "
                    + "publish the opportunity to CSV.";
            throw new OpportunityPublishingException(message, e.getMessage());
        
        }
        
    }

}
