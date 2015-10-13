package com.rga.customer.controllers;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rga.customer.entities.Customer;
import com.rga.customer.services.CustomerService;

/**
 * Controller that maps and provides RESTful services for performing <code>CRUD</code> operations in <code>CUSTOMERS</code> table
 * 
 * @author Adrian Sacuiu
 *
 */
@Controller
@RequestMapping(value = "customer")
@PreAuthorize("isAuthenticated()")
public class CustomerController {
	private static final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	/**
	 * Forwards the user to the customerPage.jsp where a form for adding a new customer can be found. CustomerPage.jsp content changes based on the add/update operation.
	 * <p>
	 * Also, adds a <code>Customer</code> object to the returned model so the Spring Form can map the values to it.
	 * <p>
	 * Can only be accessed via <code>GET</code> requests.
	 * <p>
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return A ModelAndView object with the view set to customerPage.jsp and the model containing the customer object
	 */
	@RequestMapping(value = "addCustomer", method = RequestMethod.GET)
	public ModelAndView addCustomerModel(HttpServletResponse response) {
		logger.info("Inside addCustomerModel method");

		ModelAndView mv = new ModelAndView("../views/customerPage.jsp");
		try {
			
			mv.addObject("customer", new Customer());
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return mv;
	}

	/**
	 * Adds the <code>customer</code> in the database. Uses {@link CustomerService#addCustomer(Customer)} to persist the entity.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and a success message. However, if the customer couldn't be added, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>201 (CREATED): if the customer is successfully added in the database
	 * 	<li>409 (CONFLICT): if the customer violates any constraints defined in the table
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the customer couldn't be added
	 * </ul>
	 * Can only be accessed via <code>POST</code> requests.
	 * <p>
	 * 
	 * @param customer the entity to be persisted
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation
	 */
	@RequestMapping(value = "addCustomer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> addCustomer(@ModelAttribute("customer") Customer customer, HttpServletResponse response) {
		logger.info("Inside addCustomer controller method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {

			customerService.addCustomer(customer);
			jsonMap.put("status", "success");
			jsonMap.put("message", "Customer added successfully!");

			response.setStatus(HttpServletResponse.SC_CREATED);

		} catch (ConstraintViolationException e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customer already exists!");

			response.setStatus(HttpServletResponse.SC_CONFLICT);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customer could not be added!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}

	/**
	 * Forwards the user to the customerPage.jsp where a form for updating the customer can be found. CustomerPage.jsp content changes based on the add/update operation.
	 * <p>
	 * Also, adds the <code>customer</code> object to the returned model so the Spring Form can map the values to it.
	 * <p>
	 * Can only be accessed via <code>GET</code> requests.
	 * <p>
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return A ModelAndView object with the view set to customerPage.jsp and the model containing the customer object
	 */
	@RequestMapping(value = "updateCustomer/{id}", method = RequestMethod.GET)
	public ModelAndView updateCustomerModel(@PathVariable Long id, HttpServletResponse response) {
		logger.info("Inside updateCustomerModel method");
		ModelAndView mv = new ModelAndView("../../views/customerPage.jsp");

		try {
			Customer customer = customerService.getCustomerById(id);
			mv.addObject("customer", customer);

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return mv;
	}

	/**
	 * Updates the <code>customer</code> in the database. Uses {@link CustomerService#updateCustomer(Customer)} to copy the entity state to the entity with the same identifier from the database.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and a success message. However, if the customer couldn't be updated, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>200 (OK): if the customer is successfully updated
	 * 	<li>409 (CONFLICT): if the customer violates any constraints defined in the table
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the customer couldn't be updated
	 * </ul>
	 * Can only be accessed via <code>POST</code> requests.
	 * <p>
	 * 
	 * @param customer the entity to be updated
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation
	 */
	@RequestMapping(value = "updateCustomer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> updateCustomer(@ModelAttribute("customer") Customer customer, HttpServletResponse response) {
		logger.info("Inside updateCustomer controller method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			customerService.updateCustomer(customer);
			jsonMap.put("status", "success");
			jsonMap.put("message", "Customer updated successfully!");

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (DataIntegrityViolationException e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Email already exists!");

			response.setStatus(HttpServletResponse.SC_CONFLICT);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customer could not be updated!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}

	/**
	 * Deletes the <code>customer</code> from the database. Uses {@link CustomerService#deleteCustomer(Customer)} to remove the entity from the database.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and a success message. However, if the customer couldn't be deleted, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>200 (OK): if the customer is successfully deleted
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the customer couldn't be deleted
	 * </ul>
	 * Can only be accessed via <code>DELETE</code> requests.
	 * <p>
	 * 
	 * @param customer the entity to be deleted
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation
	 */
	@RequestMapping(value = "deleteCustomer/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> deleteCustomer(@PathVariable Long id, HttpServletResponse response) {
		logger.info("Inside deleteCustomer controller method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Customer customer = customerService.getCustomerById(id);
			customerService.deleteCustomer(customer);

			jsonMap.put("status", "success");
			jsonMap.put("message", "Customer deleted successfully!");

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customer could not be deleted!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}

	/**
	 * Returns all customers from the database. Uses {@link CustomerService#getAllCustomers()} to retrieve all the entities from the database.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and the list of customers. However, if the customer couldn't be deleted, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>200 (OK): if the customer is successfully deleted
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the customer couldn't be deleted
	 * </ul>
	 * Can only be accessed via <code>GET</code> requests.
	 * <p>
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation and only if succeeded, the list of customers
	 */
	@RequestMapping(value = "getAllCustomers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getAllCustomers(HttpServletResponse response) {
		logger.info("Inside getAllCustomers controller method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			List<Customer> customers = customerService.getAllCustomers();

			jsonMap.put("status", "success");
			jsonMap.put("message", customers);

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customers could not be retrieved!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}

	/**
	 * Returns the customer with the given identifier from the database. Uses {@link CustomerService#getCustomerById()} to retrieve the entity from the database.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and the <code>customer</code> entity. However, if the customer couldn't be deleted, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>200 (OK): if the customer is successfully deleted
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the customer couldn't be deleted
	 * </ul>
	 * Can only be accessed via <code>GET</code> requests.
	 * <p>
	 * 
	 * @param id the customer identifier
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation and only if succeeded, the customer entity
	 */
	@RequestMapping(value = "getCustomerById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getCustomerById(@PathVariable Long id, HttpServletResponse response) {
		logger.info("Inside getCustomerById controller method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Customer customer = customerService.getCustomerById(id);
			if (customer == null) {
				throw new Exception("Customer could not be retrieved!");
			}

			jsonMap.put("status", "success");
			jsonMap.put("message", customer);

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "Customer could not be retrieved!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}

}
