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
	private int id;
	private String name;
	private String baseUrl;
	private String opportunitiesUrl;
	
	public Framework(int id, String name, String opportunitiesUrl) {
		this.id = id;
		this.name = name;
		this.opportunitiesUrl = opportunitiesUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Framework ["
				+ "id=" + id + ", "
				+ "name=" + name + ", "
				+ "baseUrl=" + baseUrl + ", "
				+ "opportunitiesUrl=" + opportunitiesUrl + "]";
	}

}
