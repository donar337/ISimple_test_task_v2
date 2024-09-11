package org.task.manager.producers;

import org.task.manager.producers.models.StorageType;

public class TaskStorageFactory {
    public TaskStorage createTaskStorage(String storageType) {
        switch (StorageType.valueOf(storageType.toUpperCase())) {
            case XML -> {
                return new XMLTaskStorage();
            }
            default -> {
                return null;
            }
        }
    }
}
