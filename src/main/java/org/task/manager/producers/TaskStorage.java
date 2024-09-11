package org.task.manager.producers;

import org.task.manager.models.ToDoList;

import java.io.File;
import java.io.FileNotFoundException;

public interface TaskStorage {
    ToDoList getTasks(File file) throws FileNotFoundException;
    void saveTasks(ToDoList tasks);
}

