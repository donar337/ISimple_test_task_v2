package org.task.manager.commands;

import org.task.manager.models.ToDoList;

import java.util.NoSuchElementException;

public class RemoveCommand implements Command {

    @Override
    public void execute(String[] args, ToDoList tasks) {
        int id = Utils.parseId(args);
        try {
            tasks.remove(id);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Task with id " + id + " does not exist");
        }
    }
}
