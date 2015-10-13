package com.rga.customer.services;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rga.customer.entities.User;
import com.rga.customer.services.dao.UserDao;

/**
 * Contains the tests for the methods in {@link UsersService}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml" })
public class UsersServiceTest {
	private static final Logger logger = Logger.getLogger(UsersServiceTest.class);

	@InjectMocks
	private UsersService usersService;

	@Mock
	private UserDao userDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenSuccessfulAddUserThenReturnedIdIsNotNull() {
		logger.info("Inside whenSuccessfulAddUserThenReturnedIdIsNotNull test method");

		try {
			when(userDao.addUser(any(User.class))).thenReturn(1L);

			Long userId = usersService.addUser(new User());
			assertNotNull(userId);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = Exception.class)
	public void whenAddUserThrowsExceptionThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddUserThrowsExceptionThenExceptionIsThrown test method");

		try {
			when(userDao.addUser(any(User.class))).thenThrow(new Exception("Exception! Cannot add user!"));
			usersService.addUser(new User());

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
}
