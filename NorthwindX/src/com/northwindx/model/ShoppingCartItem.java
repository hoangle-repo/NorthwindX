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

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.northwindx.model.jpa.Product;
import com.northwindx.util.PersistenceUtil;

public class ShoppingCartItem {

	private int productId;
	private int quantity;
	private BigDecimal unitPrice;
	private String productName;
	private String quantityPerUnit;

	public ShoppingCartItem() { }

	/**
	 * This constructor creates an item based on a ProductID by looking it up it in the database
	 * then passing it to loadProduct() to set the properties.
	 *
	 * @param productID	The ProductID of the product
	 */
	public ShoppingCartItem(int productID) {
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		Product product = em.createQuery("from Products where ProductId = :productID", Product.class)
				.setParameter("productID", productID)
				.getSingleResult();
		loadProduct(product);
		em.getTransaction().commit();
	}

	/**
	 * This constructor creates an item based on a Products object.
	 *
	 * @param product The Products object
	 */
	public ShoppingCartItem(Product product) {
		loadProduct(product);
	}

	/**
	 * This sets certain Products properties to thie ShoppingCartItem object. This method is
	 * protected since we don't want this to be called outside of this class or it's children.
	 * <p>
	 * Using this second method, we keep similar code in one function instead of having it
	 * in two (or more) constructors, allowing easy editing in one place.
	 *
	 * @param product	The Products object used to fill the properties
	 */
	protected void loadProduct(Product product) {
		productId = product.getProductID();
		unitPrice = product.getUnitPrice();
		productName = product.getProductName();
		quantityPerUnit = product.getQuantityPerUnit();
	}

	public BigDecimal getTotalPrice() {
		return unitPrice.multiply(new BigDecimal(quantity));
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}
}