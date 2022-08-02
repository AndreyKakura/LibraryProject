package com.kakura.libraryproject.controller.command.impl;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.CommandException;
import com.kakura.libraryproject.exception.ServiceException;
import com.kakura.libraryproject.model.service.UserService;
import com.kakura.libraryproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kakura.libraryproject.controller.command.RequestAttribute.USER;
import static com.kakura.libraryproject.controller.command.RequestAttribute.USERS;
import static com.kakura.libraryproject.controller.command.RequestParameter.*;

public class ChangeUserStatusAndRoleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userData = new HashMap<>();
        userData.put(ID, request.getParameter(ID));
        userData.put(ROLE, request.getParameter(ROLE));
        userData.put(STATUS, request.getParameter(STATUS));
        try {
            userService.updateUserStatusAndRole(userData);
            Long id = Long.parseLong(request.getParameter(ID));
            System.out.println(id);
            Optional<User> user = userService.findUser(id);
            if(user.isPresent()) {
                request.setAttribute(USER, user.get());
            }
            return new Router(PagePath.ACCOUNT_MANAGEMENT, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error has occurred while redirecting to users page: " + e);
            return new Router(PagePath.ERROR_500, Router.RouterType.REDIRECT);
        }
    }
}
