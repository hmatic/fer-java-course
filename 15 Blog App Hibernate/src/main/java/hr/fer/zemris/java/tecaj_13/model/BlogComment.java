package hr.fer.zemris.java.tecaj_13.model;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Models single blog comment in blog application.
 * @author Hrvoje Matic
 * @version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {
	/** Comment ID. */
	private Long id;
	/** Parent blog post. */
	private BlogEntry blogEntry;
	/** Commentator's email. */
	private String usersEMail;
	/** Comment message. */
	private String message;
	/** Comment creation date. */
	private Date postedOn;
	
	/**
	 * Getter for comment ID.
	 * @return comment ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Setter for comment ID.
	 * @param id comment ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for comment's parent blog post.
	 * @return comment's parent blog post
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	/**
	 * Setter for comment's parent blog post.
	 * @param blogEntry comment's parent blog post
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for commentator's email.
	 * @return commentator's email.
	 */
	@Column(length=30,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for commentator's email.
	 * @param usersEMail commentator's email.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for comment message.
	 * @return comment message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}
	/**
	 * Setter for comment message.
	 * @param message comment message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for comment creation date.
	 * @return comment creation date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}
	/**
	 * Setter for comment creation date
	 * @param postedOn comment creation date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	
	/**
	 * Returns formatted comment creation date.
	 * @return formatted comment creation date.
	 */
	public String postedOnFormatted() {
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(postedOn);
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}