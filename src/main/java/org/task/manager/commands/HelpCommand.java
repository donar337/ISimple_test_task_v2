package org.task.manager.commands;

import org.task.manager.models.ToDoList;

import java.util.Comparator;
import java.util.Map;

public class HelpCommand implements Command {
    private static final Map<String, String> descriptions = Map.of(
            "list", "list of all tasks",
            "list -s <status>", "list of all tasks with a given status",
            "complete <id>", "marks the task with the given id as completed",
            "edit <id>", "starts edit mode of the task with the given id",
            "remove <id>", "removes the task with the given id",
            "quit", "exits the program and save changes"
    );

    @Override
    public void execute(String[] args, ToDoList tasks) {
        int maxLength = descriptions.keySet().stream().max(Comparator.comparingInt(String::length)).orElse("").length();
        System.out.println("\tCommand list:");
        for (var entry : descriptions.entrySet()) {
            System.out.printf("%-" + (maxLength + 3) + "s%s%n", entry.getKey(), entry.getValue());
        }
        System.out.println("\tFormat:" + System.lineSeparator() +
                "date   - 'YYYY-MM-DD'" + System.lineSeparator() +
                "status - [new, in_progress, done]");
    }
}
