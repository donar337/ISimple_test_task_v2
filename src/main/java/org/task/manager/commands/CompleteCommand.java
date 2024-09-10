package org.task.manager.commands;

import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

public class CompleteCommand implements Command {
    @Override
    public void execute(String[] args, ToDoList tasks) {
        Task task = tasks.get(Utils.parseId(args));
        task.complete();
    }
}
