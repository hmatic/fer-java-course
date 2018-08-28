package hr.fer.zemris.java.tecaj_13.web.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Form class which handles register form inputs.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class RegisterForm extends Form {
	/**
	 * Minimum size of password in characters.
	 */
	private static final int MIN_PASSWORD_LENGTH = 8;
	/**
	 * Maximum size of register form variables in characters.
	 */
	private static final int MAX_FIELD_SIZE = 30;
	
	/** First name. */
	private String firstName;
	/** Last name. */
	private String lastName;
	/** User name. */
	private String userName;
	/** Email */
	private String email;
	/** Password */
	private String password;


	/**
	 * Default constructor.
	 */
	public RegisterForm() {
	}

	/**
	 * Getter for first name.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Getter for last name.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Getter for user name.
	 * @return user name
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Getter for email.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Creates new BlogUser object directly from register form.
	 * @return new BlogUser object created from form input data
	 */
	public BlogUser toBlogUser() {
		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(userName);
		user.setEmail(email);
		user.setPasswordHash(Password.generatePasswordHash(password));
		return user;
	}

	@Override
	public void validate() {
		// VALIDATE FIRST NAME (is not empty, is only letters, not bigger than max)
		if(firstName.isEmpty()) {
			super.addError("firstName", "First name can not be empty.");
		} else if(!isOnlyLetters(firstName)) {
			super.addError("firstName", "First name can only contain letters.");
		} else if(firstName.length()>MAX_FIELD_SIZE) {
			super.addError("firstName", "First name can contain maximum 30 characters.");
		} else {
			firstName = capitalize(firstName);
		}
		// VALIDATE LAST NAME (is not empty, is only letters, not bigger than max)
		if(lastName.isEmpty()) {
			super.addError("lastName", "Last name can not be empty.");
		} else if(!isOnlyLetters(lastName)) {
			super.addError("lastName", "Last name can only contain letters.");
		} else if(lastName.length()>MAX_FIELD_SIZE) {
			super.addError("lastName", "Last name can contain maximum 30 characters.");
		} else {
			lastName = capitalize(lastName);
		}
		// VALIDATE USER NAME (is not empty, is letters and numbers, does not already exist, not bigger than max)
		if(userName.isEmpty()) {
			super.addError("userName", "User name can not be empty.");
		} else if(!isOnlyLettersAndNumbers(userName)) {
			super.addError("userName", "Username can only contain letters and numbers.");
		} else if(userName.length()>MAX_FIELD_SIZE) {
			super.addError("userName", "Username can contain maximum 30 characters.");
		} else if(!DAOProvider.getDAO().getUserByNick(userName).isEmpty()) {
			super.addError("userName", "This username is already taken.");
		}
		// VALIDATE EMAIL(uses Apache Commons email validator)
		if(!EmailValidator.getInstance().isValid(email)) {
			super.addError("email", "Email address is not in valid format.");
		} else if(email.length()>MAX_FIELD_SIZE) {
			super.addError("email", "Email address can contain maximum 30 characters.");
		}
		// VALIDATE PASSWORD (min length)
		if(password.length()<MIN_PASSWORD_LENGTH) {
			super.addError("password", "Password must contain at least 8 characters.");
		}
	}

	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.firstName = prepareParam(req.getParameter("firstName"));
		this.lastName = prepareParam(req.getParameter("lastName"));
		this.userName= prepareParam(req.getParameter("userName"));
		this.email = prepareParam(req.getParameter("email"));
		this.password = prepareParam(req.getParameter("password"));
	}
		
	/* Helper methods. */
	
	/**
	 * Checks if input string is made only of uppercase and lowercase letters.
	 * Supports Croatian letters.
	 * @param input input string
	 * @return true if string matches criteria, false otherwise
	 */
	private static boolean isOnlyLetters(String input) {
	    return input.matches("[a-zA-ZćčšđžĆČŠĐŽ]+");
	}
	
	/**
	 * Checks if input string is made only of letters and numbers.
	 * Does not support Croatian letters.
	 * @param input input string
	 * @return true if string matches criteria, false otherwise
	 */
	private static boolean isOnlyLettersAndNumbers(String input) {
	    return input.matches("[a-zA-Z0-9]+");
	}
	
	/**
	 * Converts first letter of string into uppercase letters and returns new string.
	 * @param input input string
	 * @return copy of first string with first letter uppercase
	 */
	private static String capitalize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}
