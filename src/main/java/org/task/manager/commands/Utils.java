package org.task.manager.commands;

public class Utils {
    static int parseId(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Expecting at least one argument");
        }
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be an integer");
        }
    }
}
