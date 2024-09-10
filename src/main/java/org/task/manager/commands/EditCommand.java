package org.task.manager.commands;

import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.util.Scanner;
import java.util.function.Consumer;

public class EditCommand implements Command {
    @Override
    public void execute(String[] args, ToDoList tasks) {
        Task task = tasks.get(Utils.parseId(args));
        Scanner in = new Scanner(System.in);
        readField(in, "заголовок", task::setCaption);
        readField(in, "описание", task::setDescription);
        readField(in, "приоритет", task::setPriority);
        readField(in, "дедлайн", task::setDeadline);
    }

    private void readField(Scanner in, String field, Consumer<String> setter) {
        System.out.println("Введите новое " + field);
        if (in.hasNextLine()) {
            String line = in.nextLine();
            if (!line.isEmpty()) {
                setter.accept(line);
            }
        } else {
            throw new IllegalArgumentException("Unexpected end of input");
        }
    }
}
