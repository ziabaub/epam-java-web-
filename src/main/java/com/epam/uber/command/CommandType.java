package com.epam.uber.command;


import com.epam.uber.command.admin.*;
import com.epam.uber.command.client.ClientCancelOrderCommand;
import com.epam.uber.command.client.OrderCommand;
import com.epam.uber.command.common.ChangeLanguageCommand;
import com.epam.uber.command.common.LoginCommand;
import com.epam.uber.command.common.LogoutCommand;
import com.epam.uber.command.common.RegisterCommand;
import com.epam.uber.command.taxi.*;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {

    /**
     * needs database.
     *
     *
     * <p>
     * Admin commands.
     */
    SHOW_TAXIS(new ShowTaxisCommand()),
    FIND_TAXI(new FindTaxisCommand()),

    DELETE_TAXI(new DeleteTaxiCommand()),
    ADD_PERMISSION(new AddPermissionCommand()),

    /**
     * Common commands.
     */
    COMMON_LOGIN(new LoginCommand()),
    COMMON_LOGOUT(new LogoutCommand()),
    COMMON_REGISTER(new RegisterCommand()),

    /**
     * Taxi's commands.
     */
    GO_ONLINE(new GoOnlineCommand()),
    GO_OFFLINE(new GoOfflineCommand()),
    DISPATCHER(new DispatcherCommand()),
    ACCEPT(new AcceptCommand()),
    REACH_DESTINATION(new ReachDestinationCommand()),
    HISTORY(new HistoryCommand()),

    /**
     * Client Command
     */
    CLIENT_ORDER(new OrderCommand()),
    CANCEL_ORDER(new ClientCancelOrderCommand()),

    /**
     * no need database.
     *
     *
     * <p>
     * Admin commands.
     */
    ADD_TAXI(new AddTaxiCommand()),
    /**
     * common
     */
    COMMON_CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    /**
     * Taxi's commands.
     */
    PROFILE(new ProfileCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }

    private static final Map<String, CommandType> lookup = new HashMap<>();

    static {
        for (CommandType env : CommandType.values()) {
            lookup.put(env.toString(), env);
        }
    }

    public static CommandType getCommand(String type) {
        return lookup.get(type);
    }
}