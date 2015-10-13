package com.rga.customer.controllers;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rga.customer.entities.Customer;
import com.rga.customer.services.CustomerService;

/**
 * Contains the tests for the exposed REST services in {@link CustomerController}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml", 
									"file:src/main/webapp/WEB-INF/RGACustomerModule-servlet.xml"})
public class CustomerControllerTest {
	private static final Logger logger = Logger.getLogger(CustomerControllerTest.class);
	
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		try {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenAddCustomerModelThenReturnedViewIsTheAddCustomerPageAndReturnedModelIsNewCustomer() {
		logger.info("Inside whenAddCustomerModelThenReturnedViewIsTheAddCustomerPageAndReturnedModelIsNewCustomer test method");
		
		try {
			mockMvc.perform(get("/customer/addCustomer")
					.accept(MediaType.TEXT_HTML))
					.andExpect(status().isOk())
					.andExpect(model().size(1))
					.andExpect(model().attributeExists("customer"))
					.andExpect(view().name("../views/customerPage.jsp"));
			
		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenSuccessfulAddCustomerThenReturnJsonWithStatusSuccessAndSuccessMessage() {
		logger.info("Inside whenSuccessfulAddCustomerThenReturnJsonWithStatusSuccessAndSuccessMessage test method");
		
		try {
			when(customerService.addCustomer(any(Customer.class))).thenReturn(1L);
			
			mockMvc.perform(post("/customer/addCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isCreated())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").value("Customer added successfully!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenAddDuplicateCustomerThenReturnJsonWithStatusErrorAndErrorMessage() {
		logger.info("Inside whenAddDuplicateCustomerThenReturnJsonWithStatusErrorAndErrorMessage test method");
		
		try {
			when(customerService.addCustomer(any(Customer.class))).thenThrow(new ConstraintViolationException("Customer already exists!",null,null));
			
			mockMvc.perform(post("/customer/addCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isConflict())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Customer already exists!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenCannotAddCustomerThenReturnJsonWithStatusErrorAndErrorMessage() {
		logger.info("Inside whenCannotAddCustomerThenReturnJsonWithStatusErrorAndErrorMessage test method");
		
		try {
			when(customerService.addCustomer(any(Customer.class))).thenThrow(new Exception("Customer could not be added!"));
			
			mockMvc.perform(post("/customer/addCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isInternalServerError())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Customer could not be added!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenUpdateCustomerModelThenReturnedViewIsTheAddCustomerPageAndReturnedModelIsNewCustomer() {
		logger.info("Inside whenUpdateCustomerModelThenReturnedViewIsTheAddCustomerPageAndReturnedModelIsNewCustomer test method");
		
		try {
			when(customerService.getCustomerById(any(Long.class))).thenReturn(new Customer());
			
			mockMvc.perform(get("/customer/updateCustomer/1")
					.accept(MediaType.TEXT_HTML))
					.andExpect(status().isOk())
					.andExpect(model().size(1))
					.andExpect(model().attributeExists("customer"))
					.andExpect(view().name("../../views/customerPage.jsp"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenSuccessfulUpdateCustomerThenReturnJsonWithStatusSuccessAndSuccessMessage() {
		logger.info("Inside whenSuccessfulUpdateCustomerThenReturnJsonWithStatusSuccessAndSuccessMessage test method");
		
		try {
			when(customerService.updateCustomer(any(Customer.class))).thenReturn(new Customer());
			
			mockMvc.perform(post("/customer/updateCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").value("Customer updated successfully!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenUpdateDuplicateCustomerEmailThenReturnJsonWithStatusFalseAndErrorMessage() {
		logger.info("Inside whenUpdateDuplicateCustomerEmailThenReturnJsonWithStatusFalseAndErrorMessage test method");
		
		try {
			when(customerService.updateCustomer(any(Customer.class))).thenThrow(new DataIntegrityViolationException("Email already exists!"));
			
			mockMvc.perform(post("/customer/updateCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isConflict())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Email already exists!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenCannotUpdateCustomerThenReturnJsonWithStatusFalseAndErrorMessage() {
		logger.info("Inside whenCannotUpdateCustomerThenReturnJsonWithStatusFalseAndErrorMessage test method");
		
		try {
			when(customerService.updateCustomer(any(Customer.class))).thenThrow(new Exception("Customer could not be updated!"));
			
			mockMvc.perform(post("/customer/updateCustomer")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isInternalServerError())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Customer could not be updated!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenSuccessfulDeleteCustomerThenReturnJsonWithStatusFalseAndErrorMessage() {
		logger.info("Inside whenSuccessfulDeleteCustomerThenReturnJsonWithStatusFalseAndErrorMessage test method");
		
		try {
			
			mockMvc.perform(delete("/customer/deleteCustomer/1")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").value("Customer deleted successfully!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenDeleteNullCustomerThenReturnJsonWithStatusFalseAndErrorMessage() {
		logger.info("Inside whenDeleteNullCustomerThenReturnJsonWithStatusFalseAndErrorMessage test method");
		
		try {
			
			when(customerService.getCustomerById(any(Long.class))).thenThrow(new Exception("Customer could not be deleted!"));
			
			mockMvc.perform(delete("/customer/deleteCustomer/1")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isInternalServerError())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Customer could not be deleted!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenGetCustomerByIdFoundThenReturnJsonWithStatusSuccessAndMessageContainingTheCustomer() {
		logger.info("Inside whenGetCustomerByIdFoundThenReturnJsonWithStatusSuccessAndMessageContainingTheCustomer test method");
		
		Customer customer = new Customer();
		try {
			
			when(customerService.getCustomerById(any(Long.class))).thenReturn(customer);
			
			mockMvc.perform(get("/customer/getCustomerById/1")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").exists());
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenGetCustomerByIdNotFoundThenReturnJsonWithStatusErrorAndErrorMessage() {
		logger.info("Inside whenGetCustomerByIdNotFoundThenReturnJsonWithStatusErrorAndErrorMessage test method");
		
		try {
			when(customerService.getCustomerById(any(Long.class))).thenThrow(new Exception("Customer could not be retrieved!"));
			
			mockMvc.perform(get("/customer/getCustomerById/1")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isInternalServerError())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("Customer could not be retrieved!"));
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenGetAllCustomersThenReturnJsonWithStatusSuccessAndMessageContainingListOfCustomers() {
		logger.info("Inside whenGetAllCustomersThenReturnJsonWithStatusSuccessAndMessageContainingListOfCustomers test method");
		
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer());
		
		try {
			when(customerService.getAllCustomers()).thenReturn(customers);
			
			mockMvc.perform(get("/customer/getAllCustomers")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").exists());
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
	
	@Test
	public void whenGetAllCustomersNoCustomersFoundThenReturnJsonWithStatusSuccessAndEmptyMessage() {
		logger.info("Inside whenGetAllCustomersNoCustomersFoundThenReturnJsonWithStatusSuccessAndEmptyMessage test method");
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			when(customerService.getAllCustomers()).thenReturn(customers);
			
			mockMvc.perform(get("/customer/getAllCustomers")
					.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success"))
					.andExpect(jsonPath("$.message").doesNotExist());
			

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
}
