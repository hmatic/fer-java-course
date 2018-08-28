package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.EmailValidator;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

/**
 * Form class which handles comment form inputs.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class CommentForm extends Form {
	/**
	 * Maximum message size in characters.
	 */
	private static final int MAX_MESSAGE_SIZE = 4096;
	/**
	 * Maximum email size in characters.
	 */
	private static final int MAX_EMAIL_SIZE = 30;
	/**
	 * Input commentator's email.
	 */
	private String email;
	/**
	 * Input comment message.
	 */
	private String message;
	
	/**
	 * Default constructor.
	 */
	public CommentForm() {
	}
	
	/**
	 * Getter for input email.
	 * @return input email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Getter for input comment message.
	 * @return comment message
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public void validate() {
		if(!EmailValidator.getInstance().isValid(email)) {
			super.addError("email", "Email address is not in valid format.");
		} else if(email.length()>MAX_EMAIL_SIZE) {
			super.addError("firstName", "Email address can contain maximum 30 characters.");
		}
		
		if(message.length()<5) {
			super.addError("message", "Message must have at least 5 characters.");
		} else if(message.length()>MAX_MESSAGE_SIZE) {
			super.addError("firstName", "Message can contain maximum 4096 characters.");
		}
	}

	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.email = prepareParam(req.getParameter("email"));
		this.message = prepareParam(req.getParameter("message"));
	}
	
	/**
	 * Creates new BlogComment object directly from CommentForm.
	 * Does not modify comment's parent blogEntry, 
	 * so it has to be done from elsewhere.
	 * @return new BlogComment object created from CommentForm input data
	 */
	public BlogComment toBlogComment() {
		BlogComment comment = new BlogComment();
		comment.setUsersEMail(email);
		comment.setMessage(message);
		comment.setPostedOn(new Date());
		return comment;
	}

}
