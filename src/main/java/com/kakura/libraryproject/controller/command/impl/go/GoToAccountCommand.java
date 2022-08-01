package com.kakura.libraryproject.controller.command.impl.go;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.controller.command.SessionAttribute;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToAccountCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String login = user.getLogin();
        return new Router(PagePath.ACCOUNT, Router.RouterType.FORWARD);
    }
}
