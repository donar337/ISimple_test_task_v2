package org.task.manager.commands;

import org.task.manager.models.ToDoList;


public interface Command {
    void execute(String[] args, ToDoList tasks);
}
