package com.epam.uber.command.factory;

import com.epam.uber.command.Command;
import com.epam.uber.command.CommandType;
import com.epam.uber.command.common.HomePageCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Command.MESSAGE_ATTRIBUTE;
import static com.epam.uber.utils.MessageManager.COMMAND_ERROR_MESSAGE_KEY;

public class CommandFactory {
    private static final Logger logger = Logger.getLogger(CommandFactory.class);

    public Command getCommand(HttpServletRequest req) {
        Command currCommand = new HomePageCommand();
        String command = req.getParameter("command");
        try {
            String commandTypeValue = command.toUpperCase();
            CommandType currentType = CommandType.getCommand(commandTypeValue);
            currCommand = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Command - %s, cause exception.", command) + e);
            req.setAttribute(MESSAGE_ATTRIBUTE, COMMAND_ERROR_MESSAGE_KEY);
        }
        return currCommand;
    }
}
