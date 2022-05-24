package ai.hyperlearning.pob.core.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ai.hyperlearning.pob.model.Opportunity;

/**
 * Procurement Opportunity Repository
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public interface OpportunityRepository extends CrudRepository<Opportunity, String> {
    
    @Query("SELECT o FROM Opportunity o WHERE o.published = false")
    List<Opportunity> findNonPublished();

}
