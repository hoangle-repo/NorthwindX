/*
 * Author: Hoang Le
 * Project: NorthwindX
 */

$(document).ready(function() {
	/*
	 * This variable is used to track previous search term
	 * It is used in delete method in order to update search result list
	 * upon a record is deleted
	 */
	var previousKeyword = $('#keyword').val();
	
	// Hide buttons when no customer selected
	$('#update').hide();
	$('#delete').hide();
	$('#clear').hide();
	$('#customerID').attr('disabled',false);
	$('#create').show();
	
	// Handle search button
	$('#searchBtn').click(function() {
		search($('#keyword').val());
		return false;
	});

	// Handle click event on each search result
	$('#customerList').on('click', 'a', function() {
		
		// Show buttons when a customer selected
		$('#create').hide();
		$('#update').show();
		$('#delete').show();
		$('#clear').show();
		$('#customerID').attr('disabled',true);
		
		// Get customer data
		searchByCustomerID($(this).data('cid'));
	});

	// Handle delete button
	$('#delete').click(function() {
		deleteCustomer()
		return false;
	});

	// Handle submit button
	$('#update').click(function() {
		updateCustomer();
		return false;
	});
	
	$('#create').click(function(){
		addCustomer();
		return false;
	});

	// Clear the form
	$('#clear').click(function(){
		
		// Hide buttons upon clearing data
		$('#update').hide();
		$('#delete').hide();
		$('#customerID').attr('disabled',false);
		$('#clear').hide();
		$('#create').show();
		
		// Clear form
		clear();
		return false;
	});
	
	// Clear the search result by refreshing the page
	$('#refresh').on('click',function(){
		window.location.href="search.xhtml";
	});
});

function clear(){
	$('#customerID').val("");
	$('#companyName').val("");
	$('#contactName').val("");
	$('#contactTitle').val("");
	$('#email').val("");
	$('#address').val("");
	$('#city').val("");
	$('#region').val("");
	$('#postalCode').val("");
	$('#country').val("Select Country");
	$('#phone').val("");
	$('#fax').val("");
};

// Search function
function search(keyword) {
	previousKeyword = keyword;
	if (keyword == '') {
		getAll();
	} else {
		getByKeyword(keyword);
	}
}

// get all customers
function getAll() {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/search',
		dataType : 'json',
		success : function(data) {
			displayList(data);
		},
		error : function(jqXHR, textStatus, err) {
			alert('Failed to search customer');
		}
	});
}

// get by keyword
function getByKeyword(keyword) {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/search/'
				+ keyword,
		dataType : 'json',
		cache: false,
		success : function(data) {
			displayList(data);
		},
		error : function(jqXHR, textStatus, err) {
			alert('Failed to search customer');
		}
	});
}

// Display the data onto screen
function displayList(data) {
	var list;
	if (data == null) {
		// If no data return, return null array
		list = [];
	} else {
		// If one result return, return the data, else return array
		list = data instanceof Array ? data : [ data ];
	}

	// Delete all previous search data when start a new search
	$('#customerList li').remove();
	$.each(list, function(index, customer) {
		$('#customerList').append(
				'<li><a href="#" data-cid="' + customer.customerID + '">'
						+ customer.contactName + '</a></li>');
	});
}

// This function allow user to get detail information of a customer by providing
// customerID
function searchByCustomerID(customerID) {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/customerID/'
				+ customerID,
		dataType : 'json',
		cache: false,
		success : function(data) {
			renderJSONCustomer(data);
		},
		error : function(jqXHR, textStatus, err) {
			alert('Failed to find customer by customerID');
		}
	});
}

// This function allow user to get customer information by order
function findById() {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/1',
		dataType : 'json',
		success : function(data) {
			renderJSONCustomer(data)
		},
		error : function(jqXHR, textStatus, err) {
			alert('Failed to find customer by ID');
		}
	});
}

// Function update customer information into database
function updateCustomer() {
	// Helper function to serialize all the form fields into a JSON string

	$.ajax({
		type : 'POST',
		contentType : 'application/json;charset=utf-8',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/update/'
				+ $('#customerID').val(),
		data : JSON.stringify(customerToJSON()),
		success : function() {
			alert('Customer Updated');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Failed to update Data');
		}
	});
}

// Function add new customer to database
 function addCustomer() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json;charset=utf-8',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/add',
		data : JSON.stringify(customerToJSON()),
		success : function(response) {
			alert(response);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Error creating customer');
		}
	});
}

// Function delete customer from database
 function deleteCustomer() {
	$.ajax({
		type : 'POST',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/delete/'
				+ $('#customerID').val(),
		data: 'DELETECUSTOMER',
		beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
		success : function() {
			alert('Customer Deleted');
			search(previousKeyword);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Error Deleting Customer');
		}
	});
}

 // Function convert customer form to JSON
function customerToJSON() {
	var data = {
		"customerID" : $('#customerID').val(),
		"companyName" : $('#companyName').val(),
		"contactName" : $('#contactName').val(),
		"contactTitle" : $('#contactTitle').val(),
		"email" : $('#email').val(),
		"password": "password",
		"address" : $('#address').val(),
		"city" : $('#city').val(),
		"region" : $('#region').val(),
		"postalCode" : $('#postalCode').val(),
		"country" : $('#country').val(),
		"phone" : $('#phone').val(),
		"fax" : $('#fax').val(),
		"role" : "USER"
	};
	return data
}

// This method will distribute data to each field
function renderJSONCustomer(data) {
	$('#customerID').val(data.customerID);
	$('#companyName').val(data.companyName);
	$('#contactName').val(data.contactName);
	$('#contactTitle').val(data.contactTitle);
	$('#email').val(data.email);
	$('#address').val(data.address);
	$('#city').val(data.city);
	$('#region').val(data.region);
	$('#postalCode').val(data.postalCode);
	$('#country').val(data.country);
	$('#phone').val(data.phone);
	$('#fax').val(data.fax);
}