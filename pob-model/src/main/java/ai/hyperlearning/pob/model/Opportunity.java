package ai.hyperlearning.pob.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Public Sector Procurement Opportunity
 *
 * @author jillurquddus
 * @since 0.0.1
 */

public class Opportunity implements Serializable {

	private static final long serialVersionUID = 4837851528332532756L;
	private String uri;
	private Framework framework;
	private String title;
	private String buyer;
	private Date datePublished;
	private Date dateClosing;
	
	public Opportunity(String uri, Framework framework, String buyer, 
			String title, Date datePublished, Date dateClosing) {
		this.uri = uri;
		this.framework = framework;
		this.buyer = buyer;
		this.title = title;
		this.datePublished = datePublished;
		this.dateClosing = dateClosing;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Framework getFramework() {
		return framework;
	}

	public void setFramework(Framework framework) {
		this.framework = framework;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public Date getDateClosing() {
		return dateClosing;
	}

	public void setDateClosing(Date dateClosing) {
		this.dateClosing = dateClosing;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((framework == null) ? 0 : framework.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Opportunity other = (Opportunity) obj;
		if (framework == null) {
			if (other.framework != null)
				return false;
		} else if (!framework.equals(other.framework))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Opportunity ["
				+ "uri=" + uri + ", "
				+ "frameworkId=" + framework.getId() + ", "
				+ "buyer=" + buyer + ", "
				+ "title=" + title + ", "
				+ "datePublished=" + datePublished + ", "
				+ "dateClosing=" + dateClosing + "]";
	}

}
