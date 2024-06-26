package com.poscodx.mysite.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoadListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String contextConfigLocation = sc.getInitParameter("contextConfigLocation");

        System.out.println("Application[Mysite2] starts..." + contextConfigLocation);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
