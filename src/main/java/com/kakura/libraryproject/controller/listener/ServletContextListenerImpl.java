package com.kakura.libraryproject.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       // ConnectionPool.getInstance();
        logger.info("++++ Context Initialized: " + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      //  ConnectionPool.getInstance().destroyPool();
        logger.info("---- Context destroyed: " + sce.getServletContext().getContextPath());
    }

}
