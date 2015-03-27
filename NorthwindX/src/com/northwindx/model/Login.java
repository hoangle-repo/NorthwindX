/*************************************************************************
 *
 * DELOITTE CONSULTING
 * ___________________
 *
 *  [2013] - [2014] Deloitte Consulting, LLP
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Deloitte Consulting, LLP and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Deloitte Consulting, LLP
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Deloitte Consulting, LLP.
 *
 *************************************************************************/
package com.northwindx.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.northwindx.model.jpa.Customer;
import com.northwindx.util.PersistenceUtil;

public class Login {

    private Login() {
    } // Private default constructor makes it truly static

    private static final int MAX_COOKIE_AGE = 24 * 60 * 60; // One day in
							    // seconds

    /**
     * Returns the currently logged in user based on a "oatmealRaisin" cookie.
     * Returns null if a user is not logged in.
     * 
     * @return the Customers object
     */
    public static Customer getLoggedInUser() {
	Customer customer = null;
	String usernameValue = null;
	String passwordValue = null;

	// Get the request
	ExternalContext context = (ExternalContext) FacesContext.getCurrentInstance()
		.getExternalContext();
	HttpServletRequest request = (HttpServletRequest) context.getRequest();

	// Get information from the Cookies:
	Cookie[] cookies = request.getCookies();

	if (cookies != null) {
	    // Set username and password if user logged in
	    for (int i = 0; i < cookies.length; i++) {
		// Set usernameValue
		if (cookies[i].getName().equals("username")) {
		    usernameValue = cookies[i].getValue();
		}

		// Set passwordValue
		if (cookies[i].getName().equals("password")) {
		    passwordValue = cookies[i].getValue();
		}
	    }

	    // Check if this user is logged in
	    if (usernameValue != null) {
		customer = new Customer();
		customer.setCustomerID(usernameValue);

		if (passwordValue != null) {
		    customer.setPassword(passwordValue);
		}
	    }
	}

	return customer;
    }

    /**
     * Method overload for login(String, String, boolean).
     *
     * @param username
     *            the CustomerID of the customer trying to log in
     * @param password
     *            the Password of the customer trying to log in
     * @return a boolean that determines if the login was successful
     */
    public static boolean login(String username, String password) {
	return login(username, password, false);
    }

    /**
     * Determines if a user's credentials are correct and if so, sets a cookie
     * variable.
     * <p>
     * Begin by loading the EntityManager to access the database. Start by
     * trying to find a row where where the CustomerID matches the given
     * username. If we do have one (size() != 0) we then check if the given
     * password matches the password we have stored. If it's a match, we create
     * the cookie (see Day 5's State Tracking slides). If the rememberMe
     * argument was true, we set the cookie to exist for a day. Otherwise the
     * cookie gets deleted at the end of the session (when the browser closes)
     * 
     * @param username
     *            the customerID of the customer trying to log in
     * @param password
     *            the password of the customer trying to log in
     * @param rememberMe
     *            whether or not to keep the cookie after the session
     * @return a boolean that determines if the login was successful
     */
    public static boolean login(String username, String password, boolean rememberMe) {
	// Return value
	Boolean flag = false;

	// Initialize response
	ExternalContext context = (ExternalContext) FacesContext.getCurrentInstance()
		.getExternalContext();
	HttpServletResponse response = (HttpServletResponse) context.getResponse();
	HttpServletRequest request = (HttpServletRequest) context.getRequest();

	// Check user
	EntityManager em = PersistenceUtil.getEntityManager();
	em.getTransaction().begin();
	List<Customer> list = em
		.createQuery("from Customer c where c.customerID like :Id", Customer.class)
		.setParameter("Id", username.toUpperCase()).getResultList();
	if (list.size() != 0) {
	    Customer customer = list.get(0);
	    if (customer.getPassword().equals(password)) {
		flag = true;

		Cookie sessionIdCookie = new Cookie("sessionID", new Date().toString());
		sessionIdCookie.setMaxAge(MAX_COOKIE_AGE);
		Cookie usernameCookie = new Cookie("username", username);
		usernameCookie.setMaxAge(MAX_COOKIE_AGE);

		// Add cookies to response
		response.addCookie(sessionIdCookie);
		response.addCookie(usernameCookie);

		// Add password if remember me is checked
		if (rememberMe) {
		    Cookie passwordCookie = new Cookie("password", password);
		    passwordCookie.setMaxAge(MAX_COOKIE_AGE);
		    response.addCookie(passwordCookie);
		}
	    } else {
		flag = false;
	    }
	}

	// Commit and close transaction
	em.getTransaction().commit();
	return flag;
    }

    /**
     * Logs the user out by deleting the cookie. Setting the maxAge to 0 tells
     * the browser to delete it.
     */
    public static void logout() {
	ExternalContext context = (ExternalContext) FacesContext.getCurrentInstance()
		.getExternalContext();
	HttpServletResponse response = (HttpServletResponse) context.getResponse();
	HttpServletRequest resquest = (HttpServletRequest) context.getRequest();
	Cookie[] cookies = resquest.getCookies();

	// Delete cookies
	for (int i = 0; i < cookies.length; i++) {
	    Cookie cookie = cookies[i];
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
	}
    }
}
