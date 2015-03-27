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

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.northwindx.model.ShoppingCart;
import com.northwindx.model.ShoppingCartItem;
import com.northwindx.model.jpa.Product;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class ProductDetailsBean {

	private Product product;

	public ProductDetailsBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		loadProduct(Integer.valueOf(request.getParameter("id")));
	}

	public String addProductToCart() {

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		ShoppingCartItem cartItem = new ShoppingCartItem(product);
		cartItem.setQuantity(Integer.valueOf(params.get("quantity")));
		ShoppingCart.addItem(cartItem);

		return "showCart?faces-redirect=true";
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	protected void loadProduct(int productID) {
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		product = em.find(Product.class, productID);
		em.getTransaction().commit();
	}
}
