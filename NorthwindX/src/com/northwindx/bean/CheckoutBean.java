package com.northwindx.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.northwindx.model.Login;
import com.northwindx.model.jpa.Customer;
import com.northwindx.model.jpa.Order;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@ViewScoped
public class CheckoutBean {
	private Order order;

	public CheckoutBean() {
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String checkout() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		EntityManager em = PersistenceUtil.getEntityManager();
		
		Customer customer = Login.getLoggedInUser();

		// Check if cookie is valid
		if (customer == null) {
			Login.logout();
			return "login?faces-redirect=true";
		}

//		em.getTransaction().begin();
		//Set orderDate, CustomerID. 2 Field will be set later are Employee ID and Freight
//		order.setCustomerID(customer.getCustomerID());
//		order.setOrderDate(new Date());
//		em.persist(order);
		
		// Add each OrderDetail into the table
		
		
//		em.getTransaction().commit();
		return "orderDetails?faces-redirect=true";
	}
}
