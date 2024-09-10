package org.task.manager.producers;

import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface TaskProducers {
    ToDoList getTasks(File file) throws FileNotFoundException;
    void saveTasks(ToDoList tasks);
}

