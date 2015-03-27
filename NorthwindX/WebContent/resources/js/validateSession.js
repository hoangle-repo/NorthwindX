/*
 * Author: Hoang Le
 * File: validateSession.js
 * Uses JQuery to clear a session when tab or browser is closed
 * Reference: http://eureka.ykyuen.info/2011/02/22/jquery-javascript-capture-the-browser-or-tab-closed-event/
 */

// Variable to track when to close session
var isClosed = true;

function closeSession(){
	var cookies = $.cookie()
	for(cookie in cookies){
		$.removeCookie(cookie);
	}
}

function validateSession(){
	
	// Call close session before window and tab is closed
	window.onbeforeunload = function(){
		if(isClosed){
			closeSession();
		}
	}
	
	// Exclude case refreshing from keyboard (F5)
	$(document).bind('keypress', function(event){
		if(event.keyCode==116){
			isClosed = false;
		}
	});
	
	// Exclude case refresh by pressing links on the page
	$("a").bind('click', function(){
		isClosed = false;
	});
	
	// Exclude case submitting forms
	$("form").bind('submit', function(){
		isClosed = false;
	});
	
	// Exclude case click input on the page
	$("input[type=submit]").bind('click', function(){
		isClosed = false;
	});
}

// Execute validate Session when document is ready
$(document).ready(function(){
	validateSession();
});