package com.kakura.libraryproject.controller.command.impl.go;

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

import java.util.List;

import static com.kakura.libraryproject.controller.command.RequestAttribute.USERS;

public class GoToAccountsManagementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> users = userService.findUsers();
            request.setAttribute(USERS, users);
            return new Router(PagePath.ACCOUNTS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Error has occurred while redirecting to users page: " + e);
            return new Router(PagePath.ERROR_500, Router.RouterType.REDIRECT);
        }
    }
}
