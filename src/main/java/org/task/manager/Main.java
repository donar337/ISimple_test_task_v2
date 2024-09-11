package org.task.manager;

import org.task.manager.commands.Command;
import org.task.manager.commands.CommandsFactory;
import org.task.manager.models.ToDoList;
import org.task.manager.producers.TaskStorage;
import org.task.manager.producers.TaskStorageFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Expected at least two arguments: [input type] and [path to input file]");
            return;
        }

        ToDoList toDoList;
        TaskStorage storage;
        System.out.println("Loading tasks from, wait please");

        try {
            File inputFile = new File(args[1]);
            TaskStorageFactory factory = new TaskStorageFactory();
            storage = factory.createTaskStorage(args[0]);
            toDoList = storage.getTasks(inputFile);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("Exception while parsing input file: " + e.getMessage());
            return;
        }

        System.out.println("Tasks loaded: " + toDoList.size());

        Scanner in = new Scanner(System.in);
        CommandsFactory factory = new CommandsFactory();

        while (in.hasNextLine()) {
            String input = in.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            var flags = input.split(" ");
            Command command;
            try {
                command = factory.createCommand(flags[0]);
            } catch (IllegalArgumentException e) {
                System.out.println("Command '" + flags[0] + "' does not exist, type help to get command list");
                continue;
            }
            try {
                command.execute(Arrays.copyOfRange(flags, 1, flags.length), toDoList);
                System.out.println("Command successfully executed");
            } catch (IllegalArgumentException e) {
                System.out.println("Error while executing command: " + e.getMessage());
            }
        }

        in.close();
        System.out.println("Saving tasks, wait please");
        storage.saveTasks(toDoList);
    }
}