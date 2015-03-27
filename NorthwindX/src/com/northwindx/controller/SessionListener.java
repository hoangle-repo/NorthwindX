package com.northwindx.controller;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.northwindx.model.Login;

public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
	// No new implement needed at the moment
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
	
    }   
}
