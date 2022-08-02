package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.controller.command.SessionAttribute;
import com.kakura.libraryproject.exception.CommandException;
import com.kakura.libraryproject.exception.ServiceException;
import com.kakura.libraryproject.model.service.UserService;
import com.kakura.libraryproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.kakura.libraryproject.controller.command.RequestAttribute.MESSAGE;
import static com.kakura.libraryproject.controller.command.RequestAttribute.USER;
import static com.kakura.libraryproject.controller.command.RequestParameter.*;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String SIGN_UP_CONFIRM_MESSAGE_KEY = "confirm.sign_up";
    private static final String SIGN_UP_ERROR_MESSAGE_KEY = "error.sign_up";
    private static final String LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY = "error.login_availability";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private static final String PASSWORDS_DO_NOT_MATCH_ERROR_MESSAGE_KEY = "error.passwords_do_not_match";
    private final UserService userService = UserServiceImpl.getInstance();


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE, request.getParameter(PHONE));

        try {
            if (userService.isLoginOccupied(userData.get(LOGIN))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY); //todo was session attribute
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.isEmailOccupied(userData.get(EMAIL))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (!userService.arePasswordsEqual(userData.get(PASSWORD), userData.get(REPEATED_PASSWORD))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, PASSWORDS_DO_NOT_MATCH_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.registerUser(userData)) {
                request.removeAttribute(MESSAGE); //todo was session attribute
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, SIGN_UP_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Error has occurred while signing up: " + e);
            throw new CommandException("Error has occurred while signing up" + e);
        }
    }
}
