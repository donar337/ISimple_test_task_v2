package org.task.manager.commands;

import org.task.manager.commands.models.CommandType;

public class CommandsFactory {
    public Command createCommand(String command) {
        switch (CommandType.valueOf(command.toUpperCase())) {
            case LIST -> {
                return new ListCommand();
            }
            case NEW -> {
                return new NewCommand();
            }
            case EDIT -> {
                return new EditCommand();
            }
            case COMPLETE -> {
                return new CompleteCommand();
            }
            case REMOVE -> {
                return new RemoveCommand();
            }
            case HELP -> {
                return new HelpCommand();
            }
            default -> {
                return null;
            }
        }
    }
}
