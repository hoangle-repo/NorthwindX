package com.northwindx.controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//    	se.getSession().setMaxInactiveInterval(10*60);
    }

    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }   
}
