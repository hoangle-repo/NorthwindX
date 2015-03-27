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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.northwindx.model.jpa.Product;

@ManagedBean
@RequestScoped
public class SpecialDealsBean {

	private List<Product> productsList = new ArrayList<>();

	public SpecialDealsBean() {

		// To retrieve the data from the Northwind database and display the Special deals in two rows of 3 products in each row
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String connectionURL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=NorthwindTwo;";
			Properties connectionProps = new Properties();
			connectionProps.setProperty("user", "sa");
			connectionProps.setProperty("password", "sa");
			Connection conn = DriverManager.getConnection(connectionURL, connectionProps);

			Statement stmt = null;
			// Query to get the top 6 products by UnitsInStock from the database.
			String query = "SELECT TOP (6) * FROM Products ORDER BY UnitsInStock";

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query); 

			Product product;

			while (rs.next()) {

				product = new Product();

				product.setProductID(rs.getInt("ProductID"));
				product.setProductName(rs.getString("ProductName"));
				product.setSupplierID(rs.getInt("SupplierID"));
				product.setCategoryID(rs.getInt("CategoryID"));
				product.setQuantityPerUnit(rs.getString("QuantityPerUnit"));
				product.setUnitPrice(rs.getBigDecimal("UnitPrice"));
				product.setUnitsInStock(rs.getShort("UnitsInStock"));
				product.setUnitsOnOrder(rs.getShort("UnitsOnOrder"));
				product.setReorderLevel(rs.getShort("ReorderLevel"));
				product.setDiscontinued(rs.getBoolean("Discontinued"));

				productsList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}
}
