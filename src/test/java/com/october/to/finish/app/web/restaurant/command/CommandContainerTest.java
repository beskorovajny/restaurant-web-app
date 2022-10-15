package com.october.to.finish.app.web.restaurant.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    @Test
    void simpleTest() {
        final String commandName = "testCommand";
        final AppCommand command = new HomeCommand();
        final CommandContainer commandContainer = new CommandContainer();
        commandContainer.addCommand(commandName, command);
        assertEquals(command, CommandContainer.getCommand(commandName));
    }
}