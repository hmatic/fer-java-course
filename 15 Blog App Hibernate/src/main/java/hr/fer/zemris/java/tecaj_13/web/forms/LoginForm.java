package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Form class which handles login form inputs.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LoginForm extends Form {
	/**
	 * User name.
	 */
	private String userName;
	/**
	 * Password.
	 */
	private String password;
	/**
	 * Logged in user.
	 */
	private BlogUser loggedIn;
	
	/**
	 * Getter for user name.
	 * @return user name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Get logged in user.
	 * @return logged in user
	 */
	public BlogUser getLoggedIn() {
		return loggedIn;
	}

	@Override
	public void validate() {
		List<BlogUser> user = DAOProvider.getDAO().getUserByNick(userName);
		if(user.isEmpty()) {
			super.addError("username", "Account with this username does not exist.");
		}else if(!Password.checkPassword(password, user.get(0).getPasswordHash())) {
			super.addError("password", "Invalid password.");
		} else {
			loggedIn = user.get(0);
		}
	}

	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.userName= prepareParam(req.getParameter("username"));
		this.password = prepareParam(req.getParameter("password"));
	}
	
}
