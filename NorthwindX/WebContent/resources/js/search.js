// Handle submit create
// $('#update').click(function() {
// updateCustomer();
// return false;
// });
//

// // Create
// $('#create').click(function(){
// addCustomer();
// return false;
// });
$(document).ready(function() {

	// Handle search button
	$('#searchBtn').click(function() {
		search($('#keyword').val());
		return false;
	});

	// Handle click event on each search result
	$('#customerList').on('click', 'a', function() {
		searchByCustomerID($(this).data('cid'));
	});

	// Handle delete button
	$('#delete').click(function() {

		return false;
	});
});

// Search function
function search(keyword) {
	if (keyword == '') {
		getAll();
	} else {
		getByKeyword(keyword);
	}
};

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
};

// This function allow user to get detail information of a customer by providing
// customerID
function searchByCustomerID(customerID) {
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/NorthwindXWS/rest/customers/customerID/'
				+ customerID,
		dataType : 'json',
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
//
// function addCustomer() {
// $.ajax({
// type : 'POST',
// contentType : 'application/json; charset=utf-8',
// url : 'http://localhost:8080/NorthwindXWS/rest/customers',
// data : {request:customerToJSON()},
// success : function(data, status, jqXHR) {
// alert('Customer Created');
// },
// error : function(jqXHR, textStatus, errorThrown) {
// alert('Error adding customer: ' + textStatus);
// }
// });
// }
//
// function updateCustomer() {
// // Helper function to serialize all the form fields into a JSON string
// var customer = {
// customerID : $('#customerID').val(),
// companyName : $('#companyName').val(),
// contactName : $('#contactName').val(),
// contactTitle : $('#contactTitle').val(),
// email : $('#email').val(),
// address : $('#address').val(),
// city: $('#city').val(),
// region : $('#region').val(),
// postalCode : $('#postalCode').val(),
// country: $('#country').val(),
// phone : $('#phone').val(),
// fax : $('#fax').val()
// };
//	
// $.ajax({
// type : 'POST',
// contentType: 'application/json',
// url : 'http://localhost:8080/NorthwindXWS/rest/customers/update/'
// + $('#customerID').val(),
// data : JSON.stringify(customer),
// success : function(data) {
// alert('Customer Updated');
// },
// error : function(jqXHR, textStatus, errorThrown) {
// alert('Failed to update Data');
// }
// });
// }
//
// function deleteCustomer() {
// $.ajax({
// type : 'DELETE',
// url : 'http://localhost:8080/NorthwindXWS/rest/customers/'
// + $('#customerID').val(),
// success : function(data, textStatus, jqXHR) {
// alert('Customer Deleted');
// },
// error : function(jqXHR, textStatus, errorThrown) {
// alert('Error Deleting Customer');
// }
// });
// }

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