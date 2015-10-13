package com.rga.customer.controllers;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.rga.customer.services.SimpleAuthentication;
import com.rga.customer.services.dao.UserDao;

/**
 * Contains the tests for checking the access to the REST Services for both authenticated and anonymously situations
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml", 
									"file:src/main/webapp/WEB-INF/RGACustomerModule-servlet.xml",
									"file:src/main/webapp/WEB-INF/RGACustomerModule-security.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AuthenticationTest {
	private static final Logger logger = Logger.getLogger(AuthenticationTest.class);

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	SimpleAuthentication simpleAuthentication;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		try {
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
			
			UsersHelper usersHelper = new UsersHelper();
			usersHelper.deleteAllUsers();
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@After
	public void tearDown() {
		try {
			SecurityContextHolder.getContext().setAuthentication(null);
			
			UsersHelper usersHelper = new UsersHelper();
			usersHelper.deleteAllUsers();
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void testAuthentication() {
		logger.info("Inside testAuthentication test method");
		
		Authentication auth = null;
		
		try {
			simpleAuthentication.authenticate();
			
			auth = SecurityContextHolder.getContext().getAuthentication();
			assertNotNull(auth);
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
		
	}
	
	@Test(expected = Exception.class)
	public void whenAddCustomerModelNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenAddCustomerModelNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(get("/customer/addCustomer"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenAddCustomerModelAuthenticatedThenReturnCodeIsOk() {
		logger.info("Inside whenAddCustomerModelAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(get("/customer/addCustomer")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test(expected = Exception.class)
	public void whenAddCustomerNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenAddCustomerNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(post("/customer/addCustomer"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenAddCustomerAuthenticatedThenReturnCodeIsOk() {
		logger.info("Inside whenAddCustomerAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(post("/customer/addCustomer")).andExpect(status().isCreated());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test(expected = Exception.class)
	public void whenUpdateCustomerModelNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenUpdateCustomerModelNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(get("/customer/updateCustomer/1"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenUpdateCustomerModelAuthenticatedThenReturnCodeIsOk(){
		logger.info("Inside whenUpdateCustomerModelAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(get("/customer/updateCustomer/1")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test(expected = Exception.class)
	public void whenUpdateCustomerNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenUpdateCustomerNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(post("/customer/updateCustomer"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenUpdateCustomerAuthenticatedThenReturnCodeIsOk(){
		logger.info("Inside whenUpdateCustomerAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(post("/customer/updateCustomer")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = Exception.class)
	public void whenDeleteCustomerNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenDeleteCustomerNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(delete("/customer/deleteCustomer/1"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenDeleteCustomerAuthenticatedThenReturnCodeIsOk(){
		logger.info("Inside whenDeleteCustomerAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(delete("/customer/updateCustomer/1")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test(expected = Exception.class)
	public void whenGetCustomerByIdNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenGetCustomerByIdNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(get("/customer/getCustomerById/1"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenGetCustomerByIdAuthenticatedThenReturnCodeIsOk(){
		logger.info("Inside whenGetCustomerByIdAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(get("/customer/getCustomerById/1")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test(expected = Exception.class)
	public void whenGetAllCustomersNotAuthenticatedThenExceptionIsThrown() throws Exception{
		logger.info("Inside whenGetAllCustomersNotAuthenticatedThenExceptionIsThrown test method");
		
		try {
			mockMvc.perform(get("/customer/getAllCustomers"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}
	
	public void whenGetAllCustomersAuthenticatedThenReturnCodeIsOk(){
		logger.info("Inside whenGetAllCustomersAuthenticatedThenReturnCodeIsOk test method");
		
		try {
			simpleAuthentication.authenticate();
			
			mockMvc.perform(get("/customer/getAllCustomers")).andExpect(status().isOk());
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	/**
	 * Helper class that provides additional operations for testing purposes.
	 * 
	 * @author Adrian Sacuiu
	 *
	 */
	private class UsersHelper {
		
		/**
		 * Deletes all the users from the database so we can always know its state.
		 * 
		 * @throws Exception Points out that an problem with the database has occurred and the users were not deleted
		 */
		public void deleteAllUsers() throws Exception {
			logger.info("Inside deleteAllUsers method.");

			try {
				String hql = "DELETE FROM User";
				Query query = userDao.getCurrentSession().createQuery(hql);
				query.executeUpdate();

			} catch (Exception e) {
				printStackTraceToLogger(logger, e);
				throw e;
			}
		}
	}
}
