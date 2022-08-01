package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.RequestParameter;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.kakura.libraryproject.controller.command.SessionAttribute.*;

public class ChangeLocaleCommand implements Command {
    private static final String ENGLISH_LANGUAGE = "EN";
    private static final String RUSSIAN_LANGUAGE = "RU";
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String RUSSIAN_LOCALE = "ru_RU";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        switch (language) {
            case ENGLISH_LANGUAGE -> session.setAttribute(LOCALE, ENGLISH_LOCALE);
            case RUSSIAN_LANGUAGE -> session.setAttribute(LOCALE, RUSSIAN_LOCALE);
        }
        session.setAttribute(LANGUAGE, language);
        return new Router(currentPage, Router.RouterType.REDIRECT);
    }
}
