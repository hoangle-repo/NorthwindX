package com.northwindx;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.northwindx.model.jpa.Customer;
import com.northwindx.util.Constants;
import com.northwindx.util.PersistenceUtil;

public class App {

	public static void main(String[] args) {
		System.out.println(Constants.createMD5("password").length());
		
		
//		EntityManager em = PersistenceUtil.getEntityManager();
//		em.getTransaction().begin();
//		Query query = em.createQuery("select c from Customers c where CustomerID = :customerId",Customer.class);
//		    query.setParameter("customerId", "ALFKI");
//		    List<Customer> list = query.getResultList();
//		    if(list.size()!=0){
//			Customer customer = list.get(0);
//			if(customer.getPassword().equals("password")){
//			    System.out.println("True");
//			} else {
//			    System.out.println("False");
//			}
//		    }else{
//		    System.out.println("False out");
//		    }
		
//		em.getTransaction().commit();
//		em.close();
	}
	
	/**
	 * Sample method to use for JUnit lab
	 * @param a
	 * @param b
	 * @return
	 */
	public double divide(Integer a, Integer b) {
        return a/b;
    }

	public Integer add(Integer a, Integer b) {
        return a + b;
    }
	
	public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

}




























