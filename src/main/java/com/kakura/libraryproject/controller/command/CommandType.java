package com.kakura.libraryproject.controller.command;

import com.kakura.libraryproject.controller.command.impl.*;
import com.kakura.libraryproject.controller.command.impl.go.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {

    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    SIGN_OUT(new SignOutCommand()),
    UPDATE_ACCOUNT_DATA(new UpdateAccountDataCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    CHANGE_USER_STATUS_AND_ROLE(new ChangeUserStatusAndRoleCommand()),
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    GO_TO_HOME(new GoToHomeCommand()),
    GO_TO_ACCOUNT(new GoToAccountCommand()),
    GO_TO_ACCOUNTS_MANAGEMENT(new GoToAccountsManagementCommand()),
    GO_TO_ACCOUNT_MANAGEMENT(new GoToAccountManagementCommand()),
    GO_TO_UPDATE_ACCOUNT_DATA(new GoToUpdateAccountDataCommand()),

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
            logger.error("Error has occurred while defining command");
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
