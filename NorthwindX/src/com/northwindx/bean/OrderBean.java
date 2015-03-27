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

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.northwindx.model.Login;
import com.northwindx.model.jpa.Customer;
import com.northwindx.model.jpa.OrderDetail;
import com.northwindx.model.jpa.Order;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@ViewScoped
public class OrderBean {

	private List<Order> orderViewDetails;
	private List<OrderDetail> orderDetailsList;
	private boolean orderDetailsListRendered;
	private int first;
	private int rows;
	private int maxRows;
	private List<String> pageList;


	public OrderBean() {
		// populates the initial data table with the customers orders.
		EntityManager em = PersistenceUtil.getEntityManager();
		Customer customer = Login.getLoggedInUser();
		em.getTransaction().begin();
		orderViewDetails = em.createQuery("from Order Where CustomerID = :customerID", Order.class)
				.setParameter("customerID", customer.getCustomerID())
				.getResultList();
		em.getTransaction().commit();	
		setMaxRows(orderViewDetails.size());
		setRows(10);
		listNumPages();
	}

	public List<Order> getOrderViewDetails() {
		return orderViewDetails;
	}

	public void setOrderViewDetails(List<Order> orderViewDetails) {
		this.orderViewDetails = orderViewDetails;
	}

	public List<OrderDetail> getOrderDetailsList() {
		return orderDetailsList;
	}

	public void setOrderDetailsList(List<OrderDetail> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}
	
	public boolean isOrderDetailsListRendered() {
		return orderDetailsListRendered;
	}
	
	//This is used in the order details order table. This sets the property to true if the table has been populated.
	//There is a attribute in the data tables called "rendered" that will only show the table if this property is true.  
	public void setOrderDetailsListRendered(boolean orderDetailsListRendered) {
		this.orderDetailsListRendered = orderDetailsListRendered;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<String> getPageList() {
		return pageList;
	}

	public void setPageList(List<String> pageList) {
		this.pageList = pageList;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public void ajaxEvent(int orderID) {	
		//AJAX event function that is being called when the select command link is invoked.
		setOrderDetailsListRendered(true);
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		orderDetailsList = em.createQuery("from OrderDetail where OrderID = :orderID", OrderDetail.class)
				.setParameter("orderID", orderID)
				.getResultList();
		em.getTransaction().commit();
	}

	public void listNumPages() {
		pageList = new ArrayList<String>();
		for (int count = 0; count < Math.ceil(maxRows / rows) + 1; count++) {
			String temp = "" + count;
			pageList.add(temp);
		}
	}

	public void pageClick(String name) {
		int x = Integer.parseInt(name);
		setFirst(x * 10);
	}

}