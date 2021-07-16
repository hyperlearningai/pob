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
	private int id;
	private Framework framework;
	private String buyer;
	private String title;
	private Date datePublished;
	private Date dateClosing;
	
	public Opportunity(int id, Framework framework, String buyer, 
			String title, Date datePublished, Date dateClosing) {
		this.id = id;
		this.framework = framework;
		this.buyer = buyer;
		this.title = title;
		this.datePublished = datePublished;
		this.dateClosing = dateClosing;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		result = prime * result + ((framework == null) ? 0 : 
			framework.hashCode());
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
		Opportunity other = (Opportunity) obj;
		if (framework == null) {
			if (other.framework != null)
				return false;
		} else if (!framework.equals(other.framework))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Opportunity ["
				+ "id=" + id + ", "
				+ "framework=" + framework + ", "
				+ "buyer=" + buyer + ", "
				+ "title=" + title + ", "
				+ "datePublished=" + datePublished + ", "
				+ "dateClosing=" + dateClosing + "]";
	}

}
