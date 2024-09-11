package org.task.manager.storage;

import org.task.manager.models.ToDoList;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The {@code TaskStorage} interface defines methods for loading and saving tasks 
 * from and to persistent storage. Implementations of this interface 
 * should provide mechanisms to read tasks from a file and save tasks back to storage.
 */
public interface TaskStorage {

    /**
     * Reads tasks from the specified file and returns them as a {@link ToDoList}.
     *
     * @param file the file from which to read the tasks.
     * @return a {@link ToDoList} containing the tasks from the file.
     * @throws FileNotFoundException if the specified file does not exist or cannot be opened.
     */
    ToDoList getTasks(File file) throws FileNotFoundException;

    /**
     * Saves the specified {@link ToDoList} of tasks to persistent storage.
     *
     * @param tasks the list of tasks to be saved.
     */
    void saveTasks(ToDoList tasks);
}
