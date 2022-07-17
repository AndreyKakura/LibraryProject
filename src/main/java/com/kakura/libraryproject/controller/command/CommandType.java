package com.kakura.libraryproject.controller.command;

import com.kakura.libraryproject.controller.command.impl.*;
import com.kakura.libraryproject.controller.command.impl.go.GoToSignInCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {

    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),

    SIGN_IN(new SignInCommand()),
    GO_TO_SIGN_UP(new GoToSignInCommand()),

    DEFAULT(new DefaultCommand());

    private final Command command;

    private static final Logger logger = LogManager.getLogger();

    public Command getCommand() {
        return command;
    }

    CommandType(Command command) {
        this.command = command;
    }

    public static Command defineCommand(String commandType) {
        if (commandType == null || commandType.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandType.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            logger.error("Error has occurred while defining command: " + e);
            return CommandType.DEFAULT.getCommand();
        }
    }
}
