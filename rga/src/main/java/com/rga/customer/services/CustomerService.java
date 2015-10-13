package com.rga.customer.services;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.exception.ConstraintViolationException;

import com.rga.customer.entities.Customer;
import com.rga.customer.services.dao.CustomerDao;

/**
 * Service class containing methods that manipulate the <code>CUSTOMERS</code> table. Uses methods from the {@link CustomerDao} class for performing CRUD operations.
 * <p>
 * All operations are wrapped within a transaction.
 * 
 * @author Adrian Sacuiu
 *
 */
@Service
@Transactional
public class CustomerService {
	private static final Logger logger = Logger.getLogger(CustomerService.class);

	@Autowired
	private CustomerDao customerDao;


	/**
	 * Persists the given customer in the <code>CUSTOMERS</code> table. The customer cannot be or have the <code>email</code> property <code>null</code>.
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @param customer the customer that will be persisted
	 * 
	 * @return the id of the newly created customer
	 * 
	 * @throws ConstraintViolationException the customer has the email property null or it has a conflict with an existing customer
	 * @throws Exception indicates there is database problem and the customer could not be added
	 */
	public Long addCustomer(Customer customer) throws Exception {
		logger.info("Inside addCustomer service method.");
		Long result = null;

		try {
			result = customerDao.addCustomer(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}

	/**
	 * Copy the state of the given <code>customer</code> onto the persistent object with the same identifier in the <code>CUSTOMERS</code> table. 
	 * The customer cannot be or have the <code>email</code> property <code>null</code>. 
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @param customer the customer to be updated
	 * 
	 * @return the updated customer object
	 * 
	 * @throws ConstraintViolationException the customer has the email property null or it has a conflict with an existing customer
	 * @throws Exception indicates there is database problem and the customer could not be updated
	 */
	public Customer updateCustomer(Customer customer) throws Exception {
		logger.info("Inside updateCustomer service method.");
		Customer result = null;

		try {
			result = customerDao.updateCustomer(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}

	/**
	 * Removes the customer from the database associated with <code>customer</code> argument. 
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @param customer the persistent instance to be removed
	 * 
	 * @throws Exception indicates there is database problem and the customer could not be deleted
	 */
	public void deleteCustomer(Customer customer) throws Exception {
		logger.info("Inside updateCustomer service method.");

		try {
			customerDao.deleteCustomer(customer);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

	}

	/**
	 * Retrieves the customer with the given identifier from the database. If the customer doesn't exist, it returns null.
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @param id the customer identifier
	 * 
	 * @return the customer or <code>null</code> if not found
	 * 
	 * @throws Exception indicates there is database problem and the customer could not be retrieved
	 */
	public Customer getCustomerById(Long id) throws Exception {
		logger.info("Inside getCustomerById service method.");
		Customer result = null;

		try {
			result = customerDao.getCustomerById(id);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}

	/**
	 * Retrieves all the customers from the database. If there are no customers, an empty array will be returned.
	 * <p>
	 * This method contains a transaction.
	 * 
	 * @return a list of customers or empty array
	 * 
	 * @throws Exception Exception indicates there is database problem and the customers could not be returned
	 */
	public List<Customer> getAllCustomers() throws Exception {
		logger.info("Inside getAllCustomers service method.");
		List<Customer> result = null;

		try {
			result = customerDao.getAllCustomers();

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}
}
