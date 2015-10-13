package com.rga.customer.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Helper class used for obtaining the current Hibernate session from the Session Factory.
 * 
 * @author Adrian Sacuiu
 *
 */
public class SessionController {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionController() {
		super();
	}

	/**
	 * Obtains the current Hibernate Session
	 * 
	 * @return the current session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
