package com.rga.customer.services.dao;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rga.customer.entities.Customer;
import com.rga.customer.util.SessionController;

/**
 * Data access class containing <code>CRUD</code> operations in the <code>CUSTOMERS</code> table.
 * <p>
 * None of the operations contain a transaction.
 * 
 * @author Adrian Sacuiu
 *
 */
@Repository
public class CustomerDao extends SessionController {
	private static final Logger logger = Logger.getLogger(CustomerDao.class);

	/**
	 * Persists the given customer in the <code>CUSTOMERS</code> table. The customer cannot be or have the <code>email</code> property <code>null</code>.
	 * <p>
	 * Must be used within a transaction.
	 * 
	 * @param customer the customer that will be persisted
	 * 
	 * @return the id of the newly created customer
	 * 
	 * @throws ConstraintViolationException the customer has the email property null or it has a conflict with an existing customer
	 * @throws Exception indicates there is database problem and the customer could not be added
	 */
	public Long addCustomer(Customer customer) throws Exception {
		logger.info("Inside addCustomer dao method.");
		Long result = null;

		try {
			result = (Long) getCurrentSession().save(customer);
			logger.info("Customer added successfully!");

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
	 * Must be used within a transaction.
	 * 
	 * @param customer the customer to be updated
	 * 
	 * @return the updated customer object
	 * 
	 * @throws ConstraintViolationException the customer has the email property null or it has a conflict with an existing customer
	 * @throws Exception indicates there is database problem and the customer could not be updated
	 */
	public Customer updateCustomer(Customer customer) throws Exception {
		logger.info("Inside updateCustomer dao method.");
		Customer result = null;

		try {
			getCurrentSession().merge(customer);
			logger.info("Customer updated successfully!");

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}

	/**
	 * Removes the customer from the database associated with <code>customer</code> argument. 
	 * <p>
	 * Must be used within a transaction.
	 * 
	 * @param customer the persistent instance to be removed
	 * 
	 * @throws Exception indicates there is database problem and the customer could not be deleted
	 */
	public void deleteCustomer(Customer customer) throws Exception {
		logger.info("Inside updateCustomer dao method.");

		try {
			getCurrentSession().delete(customer);
			logger.info("Customer deleted successfully!");

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}
	}

	/**
	 * Retrieves the customer with the given identifier from the database. If the customer doesn't exist, it returns null.
	 * <p>
	 * Must be used within a transaction.
	 * 
	 * @param id the customer identifier
	 * 
	 * @return the customer or <code>null</code> if not found
	 * 
	 * @throws Exception indicates there is database problem and the customer could not be retrieved
	 */
	public Customer getCustomerById(Long id) throws Exception {
		logger.info("Inside getCustomerById dao method.");
		Customer result = null;

		try {
			result = (Customer) getCurrentSession().get(Customer.class, id);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}

	
	/**
	 * Retrieves all the customers from the database. If there are no customers, an empty array will be returned.
	 * <p>
	 * Must be used within a transaction.
	 * 
	 * @return a list of customers or empty array
	 * 
	 * @throws Exception Exception indicates there is database problem and the customers could not be returned
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() throws Exception {
		logger.info("Inside getAllCustomers dao method.");
		List<Customer> result = null;

		try {
			Query query = getCurrentSession().getNamedQuery("getAllCustomers");
			result = (List<Customer>) query.list();

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			throw e;
		}

		return result;
	}
}
