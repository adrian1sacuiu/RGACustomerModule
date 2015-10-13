package com.rga.customer.services.dao;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rga.customer.entities.User;
import com.rga.customer.util.SessionController;

/**
 * Data access class containing <code>CRUD</code> operations in the <code>USERS</code> table.
 * <p>
 * None of the operations contain a transaction.
 * 
 * @author Adrian Sacuiu
 *
 */
@Repository
public class UserDao extends SessionController {
	private static final Logger logger = Logger.getLogger(UserDao.class);

	/**
	 * Persists the given user in the <code>USERS</code> table. The user cannot be or have the <code>email</code> and <code>username</code> properties <code>null</code>. 
	 * <p>
	 * Must be used within a transaction.
	 * <p>
	 * 
	 * @param user the user that will be persisted
	 * 
	 * @return the id of the newly created user
	 * 
	 * @throws ConstraintViolationException the user has the email, password or username properties null or it has a conflict with an existing user
	 * @throws Exception indicates there is database problem and the user could not be added
	 */
	public Long addUser(User user) throws Exception {
		logger.info("Inside addUser dao method.");
		Long result = null;

		try {
			result = (Long) getCurrentSession().save(user);
			logger.info("User added successfully!");

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}
}
