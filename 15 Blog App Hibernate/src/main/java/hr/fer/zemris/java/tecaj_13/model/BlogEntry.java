package hr.fer.zemris.java.tecaj_13.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Models single blog post in blog application.
 * @author Hrvoje Matic
 * @version 1.0
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {
	/** Entry ID */
	private Long id;
	/** Entry comments. */
	private List<BlogComment> comments = new ArrayList<>();
	/** Entry creation date. */
	private Date createdAt;
	/** Entry modification date. */
	private Date lastModifiedAt;
	/** Entry title. */
	private String title;
	/** Entry text. */
	private String text;
	/** Entry creator. */
	private BlogUser creator;
	
	/**
	 * Getter for entry ID.
	 * @return entry ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Setter for entry ID.
	 * @param id entry ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for entry comments. Comments variable uses lazy loading.
	 * @return entry comments
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	/**
	 * Setter for entry comments.
	 * @param comments entry comments list
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for entry creation date.
	 * @return entry creation date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * Setter for entry creation date.
	 * @param createdAt entry creation date
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for entry modification date.
	 * @return entry modification date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	/**
	 * Setter for entry modification date.
	 * @param lastModifiedAt entry modification date
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for entry title.
	 * @return entry title
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	/**
	 * Setter for entry title.
	 * @param title entry title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for entry text.
	 * @return entry text
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}
	/**
	 * Setter for entry text.
	 * @param text entry text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Getter for entry creator.
	 * @return entry creator
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}
	/**
	 * Setter for entry creator.
	 * @param creator entry creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Returns formatted entry creation date.
	 * @return formatted entry creation date
	 */
	public String creationDateFormatted() {
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(createdAt);
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}