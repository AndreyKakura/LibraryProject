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

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String PASSWORDS_DO_NOT_MATCH_ERROR_MESSAGE_KEY = "error.passwords_do_not_match";
    private static final String UPDATE_PASSWORD_ERROR_MESSAGE_KEY = "error.update_account_password";
    private static final String PASSWORD_CHANGE_CONFIRM_MESSAGE_KEY = "confirm.change_password";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(SessionAttribute.USER);
        String login = currentUser.getLogin();
        Map<String, String> userData = new HashMap<>();
        userData.put(CURRENT_LOGIN, login);
        userData.put(OLD_PASSWORD, request.getParameter(OLD_PASSWORD));
        userData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));


        try {
            if(!userService.arePasswordsEqual(userData.get(NEW_PASSWORD), (userData.get(REPEATED_PASSWORD)))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, PASSWORDS_DO_NOT_MATCH_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
            if(userService.updatePassword(userData)) {
                Optional<User> user = userService.findUser(login);
                if(user.isPresent()) {
                    request.setAttribute(MESSAGE, PASSWORD_CHANGE_CONFIRM_MESSAGE_KEY);
                    return new Router(PagePath.ACCOUNT, Router.RouterType.REDIRECT);
                }
            }  else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, UPDATE_PASSWORD_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Error has occurred while changing user password: " + e);
            throw new CommandException("Error has occurred while changing user password" + e);
        }
        System.out.println("500");
        return new Router(PagePath.ERROR_500, Router.RouterType.REDIRECT);
    }
}
