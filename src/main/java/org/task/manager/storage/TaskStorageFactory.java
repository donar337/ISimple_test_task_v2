package org.task.manager.storage;

import org.task.manager.storage.models.StorageType;

/**
 * The {@code TaskStorageFactory} class is a factory that creates instances of
 * {@link TaskStorage} based on the specified storage type.
 */
public class TaskStorageFactory {

    /**
     * Creates and returns an instance of {@link TaskStorage} based on the specified storage type.
     *
     * @param storageType the type of storage to create (e.g., "XML").
     * @return an instance of {@link TaskStorage} corresponding to the specified storage type,
     *         or {@code null} if the storage type is not supported.
     * @throws IllegalArgumentException if the provided storage type is invalid.
     */
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
