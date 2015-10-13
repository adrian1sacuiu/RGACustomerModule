package com.rga.customer.services;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rga.customer.entities.User;
import com.rga.customer.util.RGAConstants;

/**
 * Provides a simple authentication method for the application. 
 * 
 * @author Adrian Sacuiu
 *
 */
@Service
public class SimpleAuthentication {
	private static final Logger logger = Logger.getLogger(SimpleAuthentication.class);

	@Autowired
	private UsersService usersService;

	/**
	 * Authenticate a stub user into the application. This should be used only for testing purposes.
	 * <p>
	 * First, the user is created using {@link RGAConstants#AUTH_USERNAME}, {@link RGAConstants#AUTH_PASSWORD} and {@link RGAConstants#AUTH_EMAIL} and added to the database.
	 * Then the authentication is made using that user's credentials.
	 * 
	 * @throws Exception if the authentication can't be done
	 */
	public void authenticate() throws Exception {
		Authentication auth = null;

		User user = new User();
		user.setUsername(RGAConstants.AUTH_USERNAME);
		user.setPassword(RGAConstants.AUTH_PASSWORD);
		user.setEmail(RGAConstants.AUTH_EMAIL);

		try {
			usersService.addUser(user);
			
			auth = new UsernamePasswordAuthenticationToken(RGAConstants.AUTH_USERNAME, RGAConstants.AUTH_PASSWORD);
			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
}
