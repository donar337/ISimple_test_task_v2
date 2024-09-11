package org.task.manager.commands;

import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.util.function.Predicate;

public class ListCommand implements Command {

    @Override
    public void execute(String[] args, final ToDoList tasks) {
        Predicate<Task> predicate = task -> true;
        if (args.length >= 2) {
            if (!args[0].equals("-s")) {
                throw new IllegalArgumentException("Incorrect command arguments: '" + args[0] + "'");
            }
            predicate = task -> task.getStatus() == Status.valueOf(args[1].toUpperCase());
        }
        tasks.stream().filter(predicate).forEach(System.out::println);
    }
}
