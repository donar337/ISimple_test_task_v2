package org.task.manager;

import org.task.manager.commands.Command;
import org.task.manager.commands.CommandsFactory;
import org.task.manager.models.CommandType;
import org.task.manager.models.ToDoList;
import org.task.manager.producers.TaskProducers;
import org.task.manager.producers.XMLTaskProducer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Требуется по крайней мере один аргумент программы: путь до файла с задачами");
            System.exit(0);
        }

        ToDoList toDoList = null;
        System.out.println("Пытаемся загрузить задачи, подождите");
        File inputFile = new File(args[0]);
        TaskProducers producer = new XMLTaskProducer();
        try {
            toDoList = producer.getTasks(inputFile);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("Ошибка при обработке входного файла: " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Загружено задач: " + toDoList.size());

        Scanner in = new Scanner(System.in);
        CommandsFactory factory = new CommandsFactory();

        while (in.hasNextLine()) {
            String input = in.nextLine();
            var flags = input.split(" ");
            Command command;
            try {
                CommandType commandType = CommandType.valueOf(flags[0].toUpperCase());
                command = factory.createCommand(commandType);
            } catch (IllegalArgumentException e) {
                System.out.println("Команда '" + flags[0] + "' не найдена");
                continue;
            }
            try {
                command.execute(Arrays.copyOfRange(flags, 1, flags.length), toDoList);
                System.out.println("Успешно");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка при исполнении команды: " + e.getMessage());
            }
        }

        in.close();
        producer.saveTasks(toDoList);
    }
}