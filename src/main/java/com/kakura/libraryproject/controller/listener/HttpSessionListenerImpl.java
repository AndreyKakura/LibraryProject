package com.kakura.libraryproject.controller.listener;

import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.entity.UserRole;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.kakura.libraryproject.controller.command.SessionAttribute.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String DEFAULT_LANGUAGE = "RU";

    static Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.HOME);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(ROLE, UserRole.GUEST.getRole());
        logger.info("----> Session created: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("----> Session destroyed: " + se.getSession().getId());
    }

}
