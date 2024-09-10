package org.task.manager.commands;

import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.util.List;

public interface Command {
    void execute(String[] args, ToDoList tasks);
}
