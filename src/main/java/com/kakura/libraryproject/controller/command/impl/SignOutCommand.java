package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.controller.command.SessionAttribute;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.CommandException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        servletContext.removeAttribute(user.getLogin());
        request.getSession().invalidate();
        return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
    }
}
