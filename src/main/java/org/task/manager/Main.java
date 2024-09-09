package org.task.manager;

import org.task.manager.commands.Command;
import org.task.manager.commands.CommandsFactory;
import org.task.manager.exceptions.TaskCreationException;
import org.task.manager.models.CommandType;
import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.producers.TaskProducers;
import org.task.manager.producers.XMLTaskProducer;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] arr = {"list"};
        var arr1 = Arrays.copyOfRange(arr, 1, arr.length);
        System.out.println(Arrays.toString(arr1));
        if (args == null || args.length == 0) {
            System.out.println("Требуется по крайней мере один аргумент программы: путь до файла с задачами");
            System.exit(0);
        }
        List<Task> tasks = new ArrayList<>();
        System.out.println("Пытаемся загрузить задачи, подождите");
        File inputFile = new File(args[0]);
        TaskProducers producer = new XMLTaskProducer();
        try {
            tasks = producer.getTasks(inputFile);
        } catch (FileNotFoundException | TaskCreationException e) {
            System.out.println("Ошибка при обработке входного файла: " + e.getMessage());
            System.exit(0);
        }

        {
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i).setId(i);
            }
        }

        System.out.println("Загружено задач: " + tasks.size());

        Scanner in = new Scanner(System.in);
        CommandsFactory factory = new CommandsFactory();

        outerloop:
        while (in.hasNextLine()) {
            String input = in.nextLine();
            var flags = input.split(" ");
            try {
                CommandType commandType = CommandType.valueOf(flags[0].toUpperCase());
                Command command = factory.createCommand(commandType);
                command.execute(Arrays.copyOfRange(flags, 1, flags.length), tasks);
            } catch (IllegalArgumentException e) {
                System.out.println("Команда '" + flags[0] + "' не найдена");
            }

//            switch (input) {
//                case "help" -> System.out.println("Иди нахуй");
//                case "quit", "q" -> {break outerloop;}
//                case String s when s.startsWith("list") -> {
//                    var flags = s.split(" ");
//                    if (!flags[0].equals("list")) {
//                        System.out.println("Некорректный ввод. Чтобы просмотреть список команд напишите 'help'");
//                        continue;
//                    }
//                    if (flags.length == 1) {
//                        tasks.forEach(System.out::println);
//                        continue;
//                    }
//                    if (flags.length != 3 || !flags[1].equals("-s")) {
//                        System.out.println("Некорректный ввод. Чтобы просмотреть список команд напишите 'help'");
//                        continue;
//                    }
//                    Status status;
//                    try {
//                        status = Status.valueOf(flags[2].toUpperCase());
//                        tasks.stream().filter(t -> t.getStatus() == status).forEach(System.out::println);
//                    } catch (IllegalArgumentException e) {
//                        System.out.println("Статус '" + flags[2] + "' не обнаружен. Чтобы просмотреть список команд напишите 'help'");
//                    }
//                }
//                case String s when s.startsWith("complete ") -> {
//                    var flags = s.split(" ");
//                    if (flags.length != 2) {
//                        System.out.println("Некорректный ввод. Чтобы просмотреть список команд напишите 'help'");
//                        continue;
//                    }
//                    try {
//                        int id = Integer.parseInt(flags[1]);
//                        boolean found = false;
//                        for (Task task : tasks) {
//                            if (task.getId() == id) {
//                                task.setComplete(LocalDate.now());
//                                task.setStatus(Status.DONE);
//                                found = true;
//                                break;
//                            }
//                        }
//                        if (!found) {
//                            System.out.println("Задача с данным идентефикатором не найдена");
//                        }
//                    } catch (NumberFormatException ignored) {
//                        System.out.println("Идентификатор задачи должен быть корректным целым числом");
//                    }
//                }
//                default -> System.out.println("Команда '" + input + "' не обнаружена. Чтобы просмотреть список команд напишите 'help'");
//            }
        }
        // TODO Save changes
    }
}