package org.task.manager.commands;

import org.task.manager.models.Task;

import java.util.List;
import java.util.Scanner;

public class NewCommand implements Command {

    @Override
    public void execute(String[] args, List<Task> tasks) {
        Task task = new Task();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите");
    }
}
