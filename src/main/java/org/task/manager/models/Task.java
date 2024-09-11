package org.task.manager.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The {@code Task} class represents a task in the task management system.
 * It includes attributes like id, caption, description, priority, deadline, status, and completion date.
 */
public class Task {

    /** The unique identifier of the task. */
    private int id;

    /** The title of the task. Must be between 1 and 50 characters. */
    private String caption;

    /** A detailed description of the task. */
    private String description;

    /** The priority of the task, ranging from 0 (lowest) to 10 (highest). */
    private int priority;

    /** The deadline of the task in {@link LocalDate} format. */
    private LocalDate deadline;

    /** The current status of the task (e.g., NEW, IN_PROGRESS, DONE). */
    private Status status;

    /** The date when the task was completed, or null if not yet completed. */
    private LocalDate complete;

    /**
     * Default constructor. Initializes an empty task.
     */
    public Task() {
    }

    /**
     * Constructs a new task with the specified attributes, used to create a {@link Task} from text value of it`s fields.
     *
     * @param id the unique identifier of the task as a string. Must be a valid integer.
     * @param caption the title of the task. Must be between 1 and 50 characters.
     * @param description the detailed description of the task.
     * @param priority the priority of the task, as a string. Must be an integer between 0 and 10.
     * @param deadline the deadline of the task, in a valid {@code LocalDate} format.
     * @param status the status of the task, case-insensitive.
     * @param complete the completion date of the task, or empty if not yet completed.
     * @throws IllegalArgumentException if any input is invalid (e.g., invalid id, priority, status, or deadline).
     */
    public Task(
            String id,
            String caption,
            String description,
            String priority,
            String deadline,
            String status,
            String complete
    ) {
        try {
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task id must be an integer, got '" + id + "'");
        }

        setCaption(caption);
        setDescription(description);
        setPriority(priority);

        this.deadline = LocalDate.parse(deadline);
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not a valid status '" + status.toUpperCase() + "'");
        }
        if (complete.isEmpty()) {
            this.complete = null;
        } else {
            this.complete = LocalDate.parse(complete);
        }
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the identifier of the task.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Marks the task as completed, setting the status to DONE and the completion date to the current date.
     */
    public void complete() {
        this.status = Status.DONE;
        this.complete = LocalDate.now();
    }

    /**
     * Sets the caption (title) of the task.
     * The caption must be between 1 and 50 characters in length.
     *
     * @param caption the title of the task.
     * @throws IllegalArgumentException if the caption length is outside the allowed range.
     */
    public void setCaption(String caption) {
        if (caption.isEmpty() || caption.length() > 50) {
            throw new IllegalArgumentException("Task caption length should be from 1 to 50 characters, but is " + caption.length());
        }
        this.caption = caption;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the deadline of the task.
     * The deadline must be in a valid {@code LocalDate} format.
     *
     * @param deadline the deadline date as a string.
     * @throws IllegalArgumentException if the deadline is not in a valid date format.
     */
    public void setDeadline(String deadline) {
        try {
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("'" + deadline + "' is not a valid date");
        }
    }

    /**
     * Sets the priority of the task.
     * The priority must be an integer between 0 and 10.
     *
     * @param priority the priority as a string.
     * @throws IllegalArgumentException if the priority is not a valid integer or is outside the allowed range.
     */
    public void setPriority(String priority) {
        try {
            int priorityInt = Integer.parseInt(priority);
            if (priorityInt < 0 || priorityInt > 10) {
                throw new IllegalArgumentException("Task priority should be from 0 to 10, but is " + priority);
            }
            this.priority = priorityInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task priority should be an integer, but is '" + priority + "'");
        }
    }

    /**
     * Sets the status of the task.
     *
     * @param status the status of the task.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the task ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the caption (title) of the task.
     *
     * @return the task caption.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Returns the current status of the task.
     *
     * @return the task status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the priority of the task.
     *
     * @return the task priority.
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return the task deadline.
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Returns the completion date of the task, or {@code null} if the task is not completed.
     *
     * @return the task completion date.
     */
    public LocalDate getComplete() {
        return complete;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return a formatted string representing the task attributes.
     */
    @Override
    public String toString() {
        return "Задача:" + System.lineSeparator() +
                "\tНомер = " + id + System.lineSeparator() +
                "\tЗаголовок: '" + caption + '\'' + System.lineSeparator() +
                "\tОписание: '" + description + '\'' + System.lineSeparator() +
                "\tПриоритет = " + priority + System.lineSeparator() +
                "\tДедлайн: " + deadline + System.lineSeparator() +
                "\tСтатус: " + status + System.lineSeparator() +
                (complete != null ? "\tcomplete: " + complete + System.lineSeparator() : "");
    }
}
