package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.controller.command.SessionAttribute;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.ServiceException;
import com.kakura.libraryproject.model.service.UserService;
import com.kakura.libraryproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.kakura.libraryproject.controller.command.RequestParameter.*;
import static com.kakura.libraryproject.controller.command.RequestAttribute.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String SIGN_IN_ERROR_MESSAGE_KEY = "error.sign_in";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        logger.debug(login);
        logger.debug(password);
        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                ServletContext servletContext = request.getServletContext();
                servletContext.setAttribute(login, user.get().getUserStatus());
//                String number = PhoneNumberFormatter.format(user.get().getPhoneNumber()); //TODO
//                session.setAttribute(SessionAttribute.NUMBER, number);
                session.setAttribute(SessionAttribute.USER, user.get());
                session.setAttribute(SessionAttribute.ROLE, user.get().getUserRole().getRole());
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER_LOGIN, login);
                request.setAttribute(USER_PASSWORD, password);
                request.setAttribute(MESSAGE, SIGN_IN_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD); //TODO change to FORWARD
            }
        } catch (ServiceException e) {
            logger.error("Error has occurred while signing in: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
