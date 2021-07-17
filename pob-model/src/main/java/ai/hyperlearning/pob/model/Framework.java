package ai.hyperlearning.pob.model;

import java.io.Serializable;

/**
 * Public Sector Procurement Framework
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class Framework implements Serializable {

	private static final long serialVersionUID = -4222445801889651869L;
	private String id;
	private String name;
	private String baseUrl;
	private String opportunitiesUrl;
	private String parserFullyQualifiedClassName;
	
	public Framework() {
		
	}
	
	public Framework(String id, String name, String baseUrl, 
			String opportunitiesUrl, String parserFullyQualifiedClassName) {
		this.id = id;
		this.name = name;
		this.baseUrl = baseUrl;
		this.opportunitiesUrl = opportunitiesUrl;
		this.parserFullyQualifiedClassName =parserFullyQualifiedClassName;
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

	public String getParserFullyQualifiedClassName() {
		return parserFullyQualifiedClassName;
	}

	public void setParserFullyQualifiedClassName(
			String parserFullyQualifiedClassName) {
		this.parserFullyQualifiedClassName = parserFullyQualifiedClassName;
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
				+ "baseUrl=" + baseUrl + ", "
				+ "opportunitiesUrl=" + opportunitiesUrl + ", "
				+ "parserFullyQualifiedClassName=" + 
					parserFullyQualifiedClassName + "]";
	}

}
