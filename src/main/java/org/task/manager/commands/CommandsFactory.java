package org.task.manager.commands;

import org.task.manager.models.CommandType;

public class CommandsFactory {
    public Command createCommand(CommandType commandType) {
        switch (commandType) {
            case LIST -> {
                return new ListCommand();
            }
            case NEW -> {}
//            case EDIT -> {}
//            case COMPLETE -> {}
//            case REMOVE -> {}
            default -> {return null;}
        }
//        return null;
    }
}
