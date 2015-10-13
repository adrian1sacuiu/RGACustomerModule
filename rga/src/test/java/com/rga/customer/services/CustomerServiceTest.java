package com.rga.customer.services;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rga.customer.entities.Customer;
import com.rga.customer.services.dao.CustomerDao;

/**
 * Contains the tests for the methods in {@link CustomerService}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml" })
public class CustomerServiceTest {
	private static final Logger logger = Logger.getLogger(CustomerServiceTest.class);

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerDao customerDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenSuccessfulAddCustomerThenReturnedIdIsNotNull() {
		logger.info("Inside whenSuccessfulAddCustomerThenReturnedIdIsNotNull test method");

		try {
			when(customerDao.addCustomer(any(Customer.class))).thenReturn(1L);
			Long customerId = customerService.addCustomer(new Customer());

			assertNotNull(customerId);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = Exception.class)
	public void whenAddCustomerThrowsExceptionThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenAddCustomerThrowsExceptionThenExceptionIsThrown test method");

		try {
			when(customerDao.addCustomer(any(Customer.class))).thenThrow(new Exception("Exception! Cannot add customer!"));
			customerService.addCustomer(new Customer());

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
			customer.setEmail("updateTest@test.com");

			when(customerDao.updateCustomer(customer)).thenReturn(customer);

			customer = customerService.updateCustomer(customer);
			assertEquals("updateTest@test.com", customer.getEmail());

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = Exception.class)
	public void whenUpdateNullCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenUpdateNullCustomerThenExceptionIsThrown test method");

		try {
			when(customerDao.updateCustomer(null)).thenThrow(new Exception("Cannot update null customer!"));
			customerService.updateCustomer(null);

		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void testSuccessfulDeleteCustomer() {
		logger.info("Inside testSuccessfulDeleteCustomer test method");

		try {
			customerService.deleteCustomer(new Customer());

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test(expected = Exception.class)
	public void whenDeleteNullCustomerThenExceptionIsThrown() throws Exception {
		logger.info("Inside whenDeleteNullCustomerThenExceptionIsThrown test method");

		try {
			doThrow(new Exception("Cannot delete null customer!")).when(customerDao).deleteCustomer(null);

			customerService.deleteCustomer(null);

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

			when(customerDao.getCustomerById(customer.getIdCustomer())).thenReturn(customer);
			Customer dbCustomer = customerService.getCustomerById(customer.getIdCustomer());

			assertEquals(customer, dbCustomer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenGetCustomerByIdNotFoundThenNullIsReturned() {
		logger.info("Inside whenGetCustomerByIdNotFoundThenNullIsReturned test method");

		try {
			when(customerDao.getCustomerById(0L)).thenReturn(null);

			Customer dbCustomer = customerService.getCustomerById(0L);
			assertNull(dbCustomer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenGetAllCustomersHasAtleastOneResultThenListOfCustomersIsReturned() {
		logger.info("Inside whenGetAllCustomersHasAtleastOneResultThenListOfCustomersIsReturned test method");

		List<Customer> customers = new ArrayList<Customer>();

		try {
			Customer customer = new Customer();
			customer.setEmail("test@test.com");
			customers.add(customer);

			when(customerDao.getAllCustomers()).thenReturn(customers);

			customers = customerService.getAllCustomers();

			assertNotNull(customers);
			assertEquals(1, customers.size());
			assertEquals(customer, customers.get(0));

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

}
