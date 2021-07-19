package ai.hyperlearning.pob.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ai.hyperlearning.pob.model.Opportunity;

/**
 * Opportunity Repository
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public interface OpportunityRepository extends CrudRepository<Opportunity, String> {
	
	@Query("SELECT o FROM Opportunity o WHERE o.uri = ?1 AND o.framework.id = ?2")
	List<Opportunity> findByUriAndFrameworkId(String uri, String frameworkId);

}
