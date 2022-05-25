package ai.hyperlearning.pob.data.publishers.csv;

import java.io.FileWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

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
    
    public CsvPublisher(Map<String, Object> properties) {
        super(properties);
    }
    
    public void publish(Opportunity opportunity) {
        
        LOGGER.info("Started the CSV publisher.");
        String filePath = (String) getProperties()
                .get(CSV_FILE_PATH_PROPERTY_KEY);
        try ( FileWriter fileWriter = new FileWriter(filePath, true) ) {
            
            // Build the OpenCSV Writer
            CSVWriter csvWriter = new CSVWriter(fileWriter,
                    ICSVWriter.DEFAULT_SEPARATOR,
                    ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                    ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    ICSVWriter.DEFAULT_LINE_END);
            
            // Java objects to CSV builder
            StatefulBeanToCsv<Opportunity> beanToCsv = 
                    new StatefulBeanToCsvBuilder<Opportunity>(csvWriter)
                        .build();
            
            // Append to the CSV file
            beanToCsv.write(opportunity);
            
        } catch (Exception e) {
            
            String message = "An error was encountered whilst attempting to "
                    + "publish the opportunity to CSV.";
            throw new OpportunityPublishingException(message, e.getMessage());
        
        }
        
    }

}
