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
import javax.faces.bean.ViewScoped;

import com.northwindx.model.ShoppingCart;
import com.northwindx.model.ShoppingCartItem;

@ManagedBean
@ViewScoped
public class ShoppingCartBean {
	private List<ShoppingCartItem> cart = ShoppingCart.getCart();

	public void clearCart() {
		ShoppingCart.clearCart();
		cart = ShoppingCart.getCart(); // Get the new cart
	}

	public List<ShoppingCartItem> getCart() {
		return cart;
	}

	public String setCart(List<ShoppingCartItem> cart) {
		this.cart = cart;
		
		return "";
	}
}
