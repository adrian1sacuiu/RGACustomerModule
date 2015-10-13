package com.rga.customer.services.dao;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rga.customer.entities.Customer;

/**
 * Contains the tests for the methods in {@link CustomerDao}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml" })
@Transactional
public class CustomerDaoTest {
	private static final Logger logger = Logger.getLogger(CustomerDaoTest.class);

	@Autowired
	private CustomerDao customerDao;
	
	@Test
	public void whenSuccessfulAddCustomerThenReturnedIdIsNotNull() {
		logger.info("Inside whenSuccessfulAddCustomerThenReturnedIdIsNotNull test method");

		try {
			Customer customer = new Customer();
			customer.setEmail("testEmail@test.com");
			Long customerId = customerDao.addCustomer(customer);

			assertNotNull(customerId);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddNullCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddNullCustomerThenExceptionIsThrown test method");

		try {
			customerDao.addCustomer(null);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddDuplicateCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddDuplicateCustomerThenExceptionIsThrown test method");

		try {
			Customer customer1 = new Customer();
			Customer customer2 = new Customer();
			customer1.setEmail("testEmail@test.com");
			customer2.setEmail("testEmail@test.com");
			customerDao.addCustomer(customer1);
			customerDao.addCustomer(customer2);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenAddCustomerWithNoEmailThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddCustomerWithNoEmailThenExceptionIsThrown test method");

		try {
			Customer customer = new Customer();
			customerDao.addCustomer(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test
	public void whenSuccessfulUpdateThenSameCustomerIsReturned() {
		logger.info("Inside whenSuccessfulUpdateThenSameCustomerIsReturned test method");
		
		try {
			Customer customer = new Customer();
			customer.setEmail("testEmail@test.com");
			customerDao.addCustomer(customer);

			customer.setEmail("updateTest@test.com");
			customer = customerDao.updateCustomer(customer);

			assertEquals("updateTest@test.com", customer.getEmail());
			
			customerDao.deleteCustomer(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenUpdateNullCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenUpdateNullCustomerThenExceptionIsThrown test method");

		try {
			customerDao.updateCustomer(null);

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Test
	public void testSuccessfulDeleteCustomer() {
		logger.info("Inside testSuccessfulDeleteCustomer test method");

		try {
			Customer customer = new Customer();
			customer.setEmail("test@test.com");
			customerDao.addCustomer(customer);

			customerDao.deleteCustomer(customer);

			customer = customerDao.getCustomerById(customer.getIdCustomer());

			assertNull(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = RuntimeException.class)
	public void whenDeleteNullCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenDeleteNullCustomerThenExceptionIsThrown test method");

		try {
			customerDao.deleteCustomer(null);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	@Test
	public void whenSuccessfulGetCustomerByIdThenCustomerIsReturned() {
		logger.info("Inside whenSuccessfulGetCustomerByIdThenCustomerIsReturned test method");

		try {
			Customer customer = new Customer();
			customer.setEmail("test@test.com");
			customerDao.addCustomer(customer);

			Customer dbCustomer = customerDao.getCustomerById(customer.getIdCustomer());

			assertEquals(customer, dbCustomer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenGetCustomerByIdNotFoundThenNullIsReturned() {
		logger.info("Inside whenGetCustomerByIdNotFoundThenNullIsReturned test method");

		try {
			Customer dbCustomer = customerDao.getCustomerById(0L);
			assertNull(dbCustomer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenGetAllCustomersHasAtleastOneResultThenListOfCustomersIsReturned() {
		logger.info("Inside whenGetAllCustomersHasAtleastOneResultThenListOfCustomersIsReturned test method");
		List<Customer> customers = null;

		try {
			Customer customer = new Customer();
			customer.setEmail("test@test.com");
			customerDao.addCustomer(customer);

			customers = customerDao.getAllCustomers();

			assertNotNull(customers);
			assertEquals(1, customers.size());
			assertEquals(customer, customers.get(0));

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
}
