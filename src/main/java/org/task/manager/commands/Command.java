package org.task.manager.commands;

import org.task.manager.models.Task;

import java.util.List;

public interface Command {
    public void execute(String[] args, List<Task> tasks);
}
