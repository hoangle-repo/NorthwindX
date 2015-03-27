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

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.northwindx.model.jpa.Category;
import com.northwindx.model.jpa.Product;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class ProductBean {

	private List<Product> productList;
	private int id;
	private Category catName;

	public ProductBean() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
			setId(Integer.parseInt(request.getParameter("id")));
		} else {
			id = 1;
		}

		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();

		setProductList(em.createQuery("from Product where CategoryID = ?1", Product.class).setParameter(1, id).getResultList());
		setCatName(em.createQuery("from Category where CategoryID = ?1", Category.class).setParameter(1, id).getSingleResult());

		em.getTransaction().commit();
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCatName() {
		return catName;
	}

	public void setCatName(Object object) {
		this.catName = (Category) object;
	}

}
