package com.rga.customer.services.dao;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rga.customer.entities.User;

/**
 * Contains the tests for the methods in {@link UserDao}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml" })
@Transactional
public class UserDaoTest {
	private static final Logger logger = Logger.getLogger(UserDaoTest.class);

	@Autowired
	UserDao userDao;

	@Test
	public void whenSuccessfulAddUserThenReturnedIdIsNotNull() {
		logger.info("Inside whenSuccessfulAddUserThenReturnedIdIsNotNull test method");

		User user = new User();
		user.setEmail("AA");
		user.setPassword("das");
		user.setUsername("SDAA");

		try {
			Long userId = userDao.addUser(user);
			assertNotNull(userId);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddNullUserThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddNullUserThenExceptionIsThrown test method");

		try {
			userDao.addUser(null);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddDuplicateEmailThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddDuplicateEmailThenExceptionIsThrown test method");

		try {
			User user1 = new User();
			User user2 = new User();
			user1.setEmail("testEmail@test.com");
			user2.setEmail("testEmail@test.com");
			userDao.addUser(user1);
			userDao.addUser(user2);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddDuplicateUsernameThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddDuplicateUsernameThenExceptionIsThrown test method");

		try {
			User user1 = new User();
			User user2 = new User();
			user1.setUsername("usernameTest");
			user2.setUsername("usernameTest");
			userDao.addUser(user1);
			userDao.addUser(user2);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

}
