package com.october.to.finish.restaurantwebapp.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, AppCommand> commands = new HashMap<>();

    public static AppCommand getCommand(String commandName) {
        return commands.get(commandName);
    }

    public void addCommand(String name, AppCommand command) {
        commands.put(name, command);
    }

    @Override
    public String toString() {
        return "CommandContainer = [ " + commands + " ]";
    }
}
