package org.task.manager.producers;

import org.task.manager.models.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface TaskProducers {
    public List<Task> getTasks(File file) throws FileNotFoundException;
}

