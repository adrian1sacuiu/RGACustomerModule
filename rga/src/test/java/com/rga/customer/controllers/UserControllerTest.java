package com.rga.customer.controllers;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rga.customer.entities.User;
import com.rga.customer.services.UsersService;

/**
 * Contains the tests for the exposed REST services in {@link UserController}
 * 
 * @author Adrian Sacuiu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/test-spring-config.xml", 
									"file:src/main/webapp/WEB-INF/RGACustomerModule-servlet.xml" })
public class UserControllerTest {
	private static final Logger logger = Logger.getLogger(UserControllerTest.class);

	@InjectMocks
	private UserController userController;

	@Mock
	private UsersService usersService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		try {
			MockitoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenAddUserModelThenReturnedViewIsRegisterPageAndReturnedModelIsNewUser() {
		logger.info("Inside whenAddUserModelThenReturnedViewIsRegisterPageAndReturnedModelIsNewUser test method");

		try {
			mockMvc.perform(get("/user/registerUser").accept(MediaType.TEXT_HTML)).andExpect(status().isOk()).andExpect(model().size(1)).andExpect(model().attributeExists("user"))
					.andExpect(view().name("../views/register.jsp"));

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenSuccessfulRegisterUserThenReturnJsonWithStatusSuccessAndMessageContainingTheUser() {
		logger.info("Inside whenSuccessfulRegisterUserThenReturnJsonWithStatusSuccessAndMessageContainingTheUser test method");

		try {
			when(usersService.addUser(any(User.class))).thenReturn(1L);

			mockMvc.perform(post("/user/registerUser").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("success")).andExpect(jsonPath("$.message").exists());

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenRegsiterDuplicateUserThenReturnJsonWithStatusErrorAndErrorMessage() {
		logger.info("Inside whenRegsiterDuplicateUserThenReturnJsonWithStatusErrorAndErrorMessage test method");

		try {
			when(usersService.addUser(any(User.class))).thenThrow(new ConstraintViolationException("User already exists!", null, null));

			mockMvc.perform(post("/user/registerUser").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isConflict()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(jsonPath("$.status").value("error")).andExpect(jsonPath("$.message").value("User already exists!"));

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}

	@Test
	public void whenCannotRegisterUserThenReturnJsonWithStatusErrorAndErrorMessage() {
		logger.info("Inside whenCannotRegisterUserThenReturnJsonWithStatusErrorAndErrorMessage test method");

		try {
			when(usersService.addUser(any(User.class))).thenThrow(new Exception("User could not be added!"));

			mockMvc.perform(post("/user/registerUser").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isInternalServerError())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.status").value("error"))
					.andExpect(jsonPath("$.message").value("User could not be added!"));

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
		}
	}
}
