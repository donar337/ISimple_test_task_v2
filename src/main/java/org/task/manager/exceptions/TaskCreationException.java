package org.task.manager.exceptions;

public class TaskCreationException extends IllegalArgumentException {
    public TaskCreationException(String s) {
        super("ERROR parsing task: '" + s + "'");
    }
}
