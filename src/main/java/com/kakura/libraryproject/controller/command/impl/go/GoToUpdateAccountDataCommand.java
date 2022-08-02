package com.kakura.libraryproject.controller.command.impl.go;

import com.kakura.libraryproject.controller.command.Command;
import com.kakura.libraryproject.controller.command.PagePath;
import com.kakura.libraryproject.controller.command.Router;
import com.kakura.libraryproject.entity.User;
import com.kakura.libraryproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.kakura.libraryproject.controller.command.RequestAttribute.USER;
import static com.kakura.libraryproject.controller.command.RequestParameter.*;
import static com.kakura.libraryproject.controller.command.RequestParameter.PHONE;

public class GoToUpdateAccountDataCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        User user = (User) request.getSession().getAttribute("user");
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, user.getLogin());
        userData.put(SURNAME, user.getSurname());
        userData.put(NAME, user.getName());
        userData.put(EMAIL, user.getEmail());
        userData.put(PHONE, "+" + user.getPhone());
        request.setAttribute(USER, userData);
        return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
    }
}
