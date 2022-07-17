package com.kakura.libraryproject.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@WebListener //todo
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {

    static Logger logger = LogManager.getLogger();

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        logger.info("+++<<<----> Attribute added: " + sbe.getSession().getAttribute("user_name"));
        logger.info("+++<<<---->Attribute added: " + sbe.getSession().getAttribute("current_page"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        logger.info("###<<<---->Attribute replaced: " + sbe.getSession().getAttribute("current_page"));
        logger.info("###<<<---->Attribute replaced: " + sbe.getSession().getAttribute("current_page"));
    }
}
