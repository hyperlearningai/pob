package ai.hyperlearning.pob.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Procurement Framework
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Entity
@Table(name = "frameworks")
public class Framework implements Serializable {

    private static final long serialVersionUID = -4222445801889651869L;

    @Id
    @Column(name = "id", length = 100)
    private String id;

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    private boolean enabled;

    @NotNull
    @Column(length = 1000)
    private String baseUrl;

    @NotNull
    @Column(length = 1000)
    private String opportunitiesUrl;

    @NotNull
    @Column(length = 250)
    private String parserClass;

    @NotNull
    private boolean filter;

    @Column(length = 250)
    private String keywords;

    @OneToMany(mappedBy = "framework")
    private Set<Opportunity> opportunities;

    public Framework() {

    }

    public Framework(String id, String name, boolean enabled, String baseUrl,
            String opportunitiesUrl, String parserClass, boolean filter, String keywords) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.baseUrl = baseUrl;
        this.opportunitiesUrl = opportunitiesUrl;
        this.parserClass = parserClass;
        this.filter = filter;
        this.keywords = keywords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOpportunitiesUrl() {
        return opportunitiesUrl;
    }

    public void setOpportunitiesUrl(String opportunitiesUrl) {
        this.opportunitiesUrl = opportunitiesUrl;
    }

    public String getParserClass() {
        return parserClass;
    }

    public void setParserClass(String parserClass) {
        this.parserClass = parserClass;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Set<Opportunity> getOpportunities() {
        return opportunities;
    }

    public void addOpportunity(Opportunity opportunity) {
        opportunity.setFramework(this);
        this.opportunities.add(opportunity);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Framework other = (Framework) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Framework [" 
                + "id=" + id + ", " 
                + "name=" + name + ", " 
                + "enabled=" + enabled + ", " 
                + "baseUrl=" + baseUrl + ", " 
                + "opportunitiesUrl=" + opportunitiesUrl + ", "
                + "parserClass=" + parserClass + ", " 
                + "filter=" + filter + ", " 
                + "keywords="+ keywords 
                + "]";
    }

}
