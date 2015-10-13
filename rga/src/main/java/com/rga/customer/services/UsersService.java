package com.rga.customer.services;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.exception.ConstraintViolationException;

import com.rga.customer.entities.User;
import com.rga.customer.services.dao.UserDao;

/**
 * Service class containing methods that manipulate the <code>USERS</code> table. Uses methods from the {@link UserDao} class for performing CRUD operations.
 * <p>
 * All operations are wrapped within a transaction.
 * 
 * @author Adrian Sacuiu
 *
 */
@Service
@Transactional
public class UsersService {
	private static final Logger logger = Logger.getLogger(UsersService.class);

	@Autowired
	private UserDao userDao;

	/**
	 * Persists the given user in the <code>USERS</code> table. The user cannot be or have the <code>email</code> and <code>username</code> properties <code>null</code>. 
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @param user the user that will be persisted
	 * 
	 * @return the id of the newly created user
	 * 
	 * @throws ConstraintViolationException the user has the email, password or username properties null or it has a conflict with an existing user
	 * @throws Exception indicates there is database problem and the user could not be added
	 */
	public Long addUser(User user) throws Exception {
		logger.info("Inside addUser service method.");
		Long result = null;

		try {
			String password = user.getPassword();

			// encrypt the password before saving it in the database, using BCrypt hash
			if (password != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				user.setPassword(hashedPassword);
			}
			result = userDao.addUser(user);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}
}
