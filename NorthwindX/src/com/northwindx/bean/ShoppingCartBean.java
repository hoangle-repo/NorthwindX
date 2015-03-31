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

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.northwindx.model.ShoppingCart;
import com.northwindx.model.ShoppingCartItem;

@ManagedBean
@ViewScoped
public class ShoppingCartBean {
	private List<ShoppingCartItem> cart = ShoppingCart.getCart();
	private String status;

	public ShoppingCartBean() {
		ExternalContext context = (ExternalContext) FacesContext
				.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		if (request.getParameter("status") != null
				&& request.getParameter("status").equals("0")) {
			setStatus("Not Enough Unit In Stock");
		}
		if(request.getParameter("remove") != null){
			removeItem(request.getParameter("remove"));
		}
	}

	public void clearCart() {
		ShoppingCart.clearCart();
		cart = ShoppingCart.getCart(); // Get the new cart
	}

	private void removeItem(String removeId) {
		for (int i = 0;i<cart.size();i++) {
			if ((cart.get(i).getProductId()+"").equals(removeId)) {
				cart.remove(i);
			}
		}
	}

	public List<ShoppingCartItem> getCart() {
		return cart;
	}

	public String setCart(List<ShoppingCartItem> cart) {
		this.cart = cart;

		return "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
