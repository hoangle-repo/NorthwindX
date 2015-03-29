package com.northwindx.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.northwindx.model.jpa.Customer;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class SearchBean {

    private List<Customer> customerList = new ArrayList<>();
    private List<Customer> companyList = new ArrayList<>();
    private int resultNumber;
    private String keywords;
    private String type;

    public SearchBean() {
	ExternalContext context = (ExternalContext) FacesContext.getCurrentInstance()
		.getExternalContext();
	HttpServletRequest request = (HttpServletRequest) context.getRequest();
	
	// Get keywords and search type from browser
	setKeywords(request.getParameter("keywords"));
	setType(request.getParameter("type"));
	
	// Search if user hit search button
	if (getKeywords() != null) {
	    EntityManager em = PersistenceUtil.getEntityManager();
	    em.getTransaction().begin();
	    
	    // Search company only if user ask for it
	    if (getType()!=null && getType().equals("company")) {
		setCompanyList(em.createQuery(
			"from Customer c where c.companyName like \'%"
				+ getKeywords() + "%\'").getResultList());
		setResultNumber(getCompanyList().size());
		
	    } else {
		setType("customer");
		setCustomerList(em.createQuery(
			"from Customer c where c.contactName like \'%"
				+ getKeywords() + "%\'").getResultList());
		setResultNumber(getCustomerList().size());
	    }
	    em.getTransaction().commit();
	}
    }

    public List<Customer> getCustomerList() {
	return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
	this.customerList = customerList;
    }

    public String getKeywords() {
	return keywords;
    }

    public void setKeywords(String keywords) {
	this.keywords = keywords;
    }

    public List<Customer> getCompanyList() {
	return companyList;
    }

    public void setCompanyList(List<Customer> companyList) {
	this.companyList = companyList;
    }

    public int getResultNumber() {
	return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
	this.resultNumber = resultNumber;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
