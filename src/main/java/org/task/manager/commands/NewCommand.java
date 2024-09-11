package org.task.manager.commands;

import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.util.Scanner;
import java.util.function.Consumer;

public class NewCommand implements Command {

    @Override
    public void execute(String[] args, ToDoList tasks) {
        Task task = new Task();
        Scanner in = new Scanner(System.in);

        readField(in, "caption", task::setCaption);
        readField(in, "description", task::setDescription);
        readField(in, "priority", task::setPriority);
        readField(in, "deadline", task::setDeadline);
        task.setStatus(Status.NEW);
        tasks.add(task);
    }

    private void readField(Scanner in, String field, Consumer<String> setter) {
        System.out.println("Enter " + field);
        if (in.hasNextLine()) {
            String line = in.nextLine();
            setter.accept(line);
        } else {
            throw new IllegalArgumentException("Unexpected end of input");
        }
    }
}
