package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.controller.command.SessionAttribute;
import com.kakura.libraryproject.entity.User;
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
import java.util.Optional;

import static com.kakura.libraryproject.controller.command.RequestAttribute.MESSAGE;
import static com.kakura.libraryproject.controller.command.RequestAttribute.USER;
import static com.kakura.libraryproject.controller.command.RequestParameter.*;


public class UpdateAccountDataCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String UPDATE_ACCOUNT_DATA_CONFIRM_MESSAGE_KEY = "confirm.update_account_data";
    private static final String UPDATE_ACCOUNT_DATA_ERROR_MESSAGE_KEY = "error.update_account_data";
    private static final String INCORRECT_PASSWORD_ERROR_MESSAGE_KEY = "error.update_account_data.incorrect_password";
    private static final String PASSWORD_REQUIREMENT_ERROR_MESSAGE_KEY = "error.update_account_data.password_requirement";
    private static final String LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY = "error.login_availability";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(SessionAttribute.USER);
        String login = currentUser.getLogin();
        String id = Long.toString(currentUser.getId());
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
//        userData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
//        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE, request.getParameter(PHONE));
        userData.put(ID, id);
//        if (!login.equals(userData.get(LOGIN)) && userData.get(PASSWORD).isEmpty()) {
//            request.setAttribute(USER, userData);
//            request.setAttribute(MESSAGE, PASSWORD_REQUIREMENT_ERROR_MESSAGE_KEY);
//            return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
//        }
        try {
            if (!login.equals(userData.get(LOGIN)) && userService.isLoginOccupied(userData.get(LOGIN))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
            Optional<User> user = userService.findUser(login);
            if (user.isPresent() && !user.get().getEmail().equals(userData.get(EMAIL))
                    && userService.isEmailOccupied(userData.get(EMAIL))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
            //userData.put(CURRENT_LOGIN, login);
            if (userService.findUser(login, userData.get(PASSWORD)).isPresent()) {
                if (userService.updateUserData(userData)) {
                    user = userService.findUser(userData.get(LOGIN));
                    if (user.isPresent()) {
                        session.setAttribute(SessionAttribute.USER, user.get());
                        //session.setAttribute(SessionAttribute.PHONE, userData.get(PHONE));
                        session.removeAttribute(SessionAttribute.MESSAGE);
                        return new Router(PagePath.ACCOUNT, Router.RouterType.REDIRECT);
                    }
                } else {
                    request.setAttribute(USER, userData);
                    request.setAttribute(MESSAGE, UPDATE_ACCOUNT_DATA_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
                }
            } else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, INCORRECT_PASSWORD_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }

        } catch (ServiceException e) {
            logger.error("Error has occurred while updating user account: " + e);
            throw new CommandException("Error has occurred while updating user account", e);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}

