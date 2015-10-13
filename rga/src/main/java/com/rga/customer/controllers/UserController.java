package com.rga.customer.controllers;

import static com.rga.customer.util.RgaUtils.printStackTraceToLogger;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rga.customer.entities.User;
import com.rga.customer.services.UsersService;

/**
 * Controller that maps and provides RESTful services for performing <code>CRUD</code> operations in <code>USERS</code> table
 * 
 * @author Adrian Sacuiu
 *
 */
@Controller
@RequestMapping(value = "user")
@PreAuthorize("isAnonymous()")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UsersService userService;

	/**
	 * Forwards the user to the register.jsp page where a form for registering a new user can be found.
	 * <p>
	 * Also, adds a <code>User</code> object to the returned model  so the Spring Form can map the values to it.
	 * <p>
	 * Can only be accessed via <code>GET</code> requests.
	 * <p>
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return A ModelAndView object with the view set to register.jsp and the model containing the user object
	 */
	@RequestMapping(value = "registerUser", method = RequestMethod.GET)
	public ModelAndView addUserModel(HttpServletResponse response) {
		logger.info("Inside addUserModel method");

		ModelAndView mv = new ModelAndView("../views/register.jsp");
		try {
			mv.addObject("user", new User());

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return mv;
	}
	/**
	 * Adds the <code>user</code> in the database. Uses {@link UsersService#addUser(User)} to persist the entity.
	 * <p>
	 * If the operations succeeds then returns a <code>Json</code> with status <code>success></code> and a success message. However, if the user couldn't be registered, then
	 * the <code>Json</code> will have a status <code>error</code> and contain an error message.
	 * <p>
	 * Response statuses:
	 * <ul>
	 * 	<li>201 (CREATED): if the user is successfully added in the database
	 * 	<li>409 (CONFLICT): if the user violates any constraints defined in the table
	 * 	<li>500 (INTERNAL SERVER ERROR): if there is any database problems and the user couldn't be registered
	 * </ul>
	 * Can only be accessed via <code>POST</code> requests.
	 * <p>
	 * 
	 * @param user the entity to be persisted
	 * 
	 * @param response the HttpServlet response
	 * 
	 * @return a <code>Json</code> with the result of the operation
	 */
	@RequestMapping(value = "registerUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> registerUser(@ModelAttribute("user") User user, HttpServletResponse response) {
		logger.info("Inside registerUser method");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			String decryptedPassword = user.getPassword();
			userService.addUser(user);

			user.setPassword(decryptedPassword);

			jsonMap.put("status", "success");
			jsonMap.put("message", user);

			response.setStatus(HttpServletResponse.SC_CREATED);

		} catch (ConstraintViolationException e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "User already exists!");

			response.setStatus(HttpServletResponse.SC_CONFLICT);

		} catch (Exception e) {
			printStackTraceToLogger(logger, e);
			jsonMap.put("status", "error");
			jsonMap.put("message", "User could not be added!");

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return jsonMap;
	}
}
