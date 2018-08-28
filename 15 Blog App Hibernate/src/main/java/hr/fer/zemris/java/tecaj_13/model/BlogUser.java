package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Models single blog user in blog application.
 * @author Hrvoje Matic
 * @version 1.0
 */
@Entity
@Table(name="blog_users")
public class BlogUser {
	/** User ID. */
	private Long id;
	/** User's first name. */
	private String firstName;
	/** User's last name. */
	private String lastName;
	/** User's nick(username). */
	private String nick;
	/** User email. */
	private String email;
	/** Password hashed with SHA-1 */
	private String passwordHash;
	/** List of all blog posts created by user. */
	private List<BlogEntry> entries = new ArrayList<>();;
	
	/**
	 * Default constructor.
	 */
	public BlogUser() {
	}
	
	/**
	 * Getter for user ID.
	 * @return user ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	/**
	 * Setter for user ID.
	 * @param id user ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter for user first name.
	 * @return user first name
	 */
	@Column(length=30, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Setter for user first name.
	 * @param firstName user first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for user last name.
	 * @return user last name
	 */
	@Column(length=30, nullable=false)
	public String getLastName() {
		return lastName;
	}
	/**
	 * Setter for user last name.
	 * @param lastName user last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for user nick.
	 * @return user nick
	 */
	@Column(length=30, nullable=false, unique=true)
	public String getNick() {
		return nick;
	}
	/**
	 * Setter for user nick.
	 * @param nick user nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter for user email.
	 * @return user email
	 */
	@Column(length=30, nullable=false)
	public String getEmail() {
		return email;
	}
	/**
	 * Setter for user email.
	 * @param email user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for password hash.
	 * @return password hash
	 */
	@Column(length=40, nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	/**
	 * Setter for password hash.
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter for user's blog posts.
	 * @return list of user's blog posts
	 */
	@OneToMany(mappedBy="creator")
	public List<BlogEntry> getEntries() {
		return entries;
	}
	/**
	 * Setter for user's blog posts.
	 * @param entries list of user's blog posts
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
