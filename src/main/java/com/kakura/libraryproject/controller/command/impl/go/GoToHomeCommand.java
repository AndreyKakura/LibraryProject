package com.kakura.libraryproject.controller.command.impl.go;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToHomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.HOME, Router.RouterType.FORWARD);
    }
}
