package org.task.manager.commands;

import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.util.List;
import java.util.function.Predicate;

public class ListCommand implements Command {

    @Override
    public void execute(String[] args, final ToDoList tasks) {
        Predicate<Task> predicate = task -> true;;
        if (args.length >= 2) {
            if (!args[0].equals("-s")) {
                System.out.println("Некорректныe аргументы команды");
                return;
            }
            try {
                Status status = Status.valueOf(args[1].toUpperCase());
                predicate = task -> task.getStatus() == status;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректныe аргументы команды");
                return;
            }
        }
        tasks.stream().filter(predicate).forEach(System.out::println);
    }
}
