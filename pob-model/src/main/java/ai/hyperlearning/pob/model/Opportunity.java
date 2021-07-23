package ai.hyperlearning.pob.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.hash.Hashing;

/**
 * Public Sector Procurement Opportunity
 *
 * @author jillurquddus
 * @since 0.0.1
 */

@Entity
@Table(
		name = "opportunities", 
		uniqueConstraints = @UniqueConstraint(
				name = "uniqueUriAndFramework", 
				columnNames = { "uri", "frameworkId" }))
public class Opportunity implements Serializable {

	private static final long serialVersionUID = 4837851528332532756L;
	
	@Id
	@Column(length = 250)
	private String id;
	
	@NotNull
	@Column(length = 1000)
	private String uri;
	
	@Column(length = 1000)
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "frameworkId", referencedColumnName = "id", nullable = false)
	private Framework framework;
	
	@NotNull
	@Column(length = 500)
	private String title;
	
	@NotNull
	@Column(length = 500)
	private String buyer;
	
	@NotNull
	@Column(length = 1000)
	private String summary;
	
	@Basic
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate datePublished;
	
	@Basic
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateClosing;
	
	@NotNull
	private boolean published = false;
	
	@NotNull
	private boolean indexed = false;
	
	public Opportunity() {
		
	}
	
	public Opportunity(String uri, String url, Framework framework, 
			String title, String buyer, String summary, 
			LocalDate datePublished, LocalDate dateClosing) {
		setId(uri, framework);
		this.uri = uri;
		this.url = url;
		this.framework = framework;
		this.title = title;
		this.buyer = buyer;
		this.summary = summary;
		this.datePublished = datePublished;
		this.dateClosing = dateClosing;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setId(String uri, Framework framework) {
		this.id = Hashing.sha512().hashString(framework.getId() + "-" + uri, 
				StandardCharsets.UTF_8).toString();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Framework getFramework() {
		return framework;
	}

	public void setFramework(Framework framework) {
		this.framework = framework;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDate getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(LocalDate datePublished) {
		this.datePublished = datePublished;
	}

	public LocalDate getDateClosing() {
		return dateClosing;
	}

	public void setDateClosing(LocalDate dateClosing) {
		this.dateClosing = dateClosing;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
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
		Opportunity other = (Opportunity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Opportunity ["
				+ "id=" + id + ", "
				+ "uri=" + uri + ", "
				+ "url=" + url + ", "
				+ "frameworkId=" + framework.getId() + ", "
				+ "title=" + title + ", "
				+ "buyer=" + buyer + ", "
				+ "summary=" + summary + ", "
				+ "datePublished=" + datePublished.toString() + ", "
				+ "dateClosing=" + dateClosing.toString() + ", "
				+ "published=" + published + ", "
				+ "indexed=" + indexed + "]";
	}

}
