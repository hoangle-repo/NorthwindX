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

import com.northwindx.model.jpa.Customer;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class AddCustomerBean {

	private Customer tempCustomer = new Customer();

	public AddCustomerBean() { }

	public Customer getTempCustomer() {
		return tempCustomer;
	}

	public void setTempCustomer(Customer tempCustomer) {
		this.tempCustomer = tempCustomer;
	}

	public String submitChanges() {
		tempCustomer.setPassword("password");  // (Over)write any set password to make all customers' passwords uniform
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(tempCustomer);
		em.getTransaction().commit();
		
		return "login?faces-redirect=true";
	}

}