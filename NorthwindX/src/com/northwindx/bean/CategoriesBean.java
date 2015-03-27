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
import javax.persistence.EntityManager;

import com.northwindx.model.jpa.Category;
import com.northwindx.util.PersistenceUtil;

@ManagedBean
@RequestScoped
public class CategoriesBean {

	private List<Category> categoriesList;

	public CategoriesBean() {
		EntityManager em = PersistenceUtil.getEntityManager();
		em.getTransaction().begin();
		setCategoriesList(em.createQuery("from Category", Category.class).getResultList());
		em.getTransaction().commit();
	}

	public List<Category> getCategoriesList() {
		return categoriesList;
	}

	public void setCategoriesList(List<Category> categoriesList) {
		this.categoriesList = categoriesList;
	}
}
