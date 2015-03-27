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
package com.northwindx.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

import com.northwindx.model.Login;
import com.northwindx.model.jpa.Customer;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class EditCustomerBean {

	private Customer tempCustomer;

	public EditCustomerBean() {
		EntityManager em = PersistenceUtil.getEntityManager();
		Customer customer = Login.getLoggedInUser();
		em.getTransaction().begin();
		tempCustomer = em.createQuery("from Customer Where CustomerID = :customerID", Customer.class)
				.setParameter("customerID", customer.getCustomerID())
				.getSingleResult();
		em.getTransaction().commit();
	}

	public Customer getTempCustomer() {
		return tempCustomer;
	}

	public void setTempCustomer(Customer tempCustomer) {
		this.tempCustomer = tempCustomer;
	}

	public String submitChanges() {
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		// Don't have to do anything else but commit the changes since tempCustomer is already a managed entity
		// http://meri-stuff.blogspot.com/2012/03/jpa-tutorial.html#EntityManagerUpdates,InsertsandDeletesUpdate
		em.getTransaction().commit();
		
		return "";
	}
	
	public String delete() {
		Login.logout();
		
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(tempCustomer);
		em.getTransaction().commit();
		
		return "default?faces-redirect=true";
	}

}