package com.northwindx.bean;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.northwindx.model.Login;
import com.northwindx.model.ShoppingCart;
import com.northwindx.model.ShoppingCartItem;
import com.northwindx.model.jpa.Customer;
import com.northwindx.model.jpa.Order;
import com.northwindx.model.jpa.OrderDetail;
import com.northwindx.model.jpa.OrderDetailPK;
import com.northwindx.model.jpa.Product;
import com.northwindx.util.PersistenceUtil;

@ManagedBean(name = "checkoutBean")
@RequestScoped
public class CheckoutBean {
	private Order order = new Order();

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
		boolean doCommit = true;
		Customer customer = Login.getLoggedInUser();

		em.getTransaction().begin();
		order.setCustomer(customer);
		order.setOrderDate(new Date());
		em.persist(order);
		em.getTransaction().commit();

		em.getTransaction().begin();

		// Add each OrderDetail into the table
		List<ShoppingCartItem> cartItems = ShoppingCart.getCart();
		for (ShoppingCartItem item : cartItems) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderDetailsPK(new OrderDetailPK(order.getOrderID(),
					item.getProductId()));
			orderDetail.setQuantity((short) item.getQuantity());
			orderDetail.setUnitPrice(item.getUnitPrice());
			orderDetail.setDiscount(0);
			orderDetail.setOrder(order);
			Product tempProduct = (Product) em
					.createQuery("from Product p where p.productID like :id")
					.setParameter("id", item.getProductId()).getSingleResult();
			orderDetail.setProduct(tempProduct);
			
			if (tempProduct.getUnitsInStock() < orderDetail.getQuantity()) {
				doCommit = false;
				break;
			} else {
				em.persist(orderDetail);
				tempProduct.setUnitsInStock((short) (tempProduct
						.getUnitsInStock() - orderDetail.getQuantity()));
			}
		}

		if (doCommit) {
			em.getTransaction().commit();
			ShoppingCart.clearCart();
			return "orderDetails?faces-redirect=true";
		} else {
			em.getTransaction().rollback();
			return "showCart?faces-redirect=true&status=0";
		}

	}
}
