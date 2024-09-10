package org.task.manager.commands;

import org.task.manager.models.CommandType;

public class CommandsFactory {
    public Command createCommand(CommandType commandType) {
        switch (commandType) {
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
            default -> {
                return null;
            }
        }
    }
}
