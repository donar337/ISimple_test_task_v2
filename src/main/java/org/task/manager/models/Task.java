package org.task.manager.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Task {
    private int id;
    private String caption;
    private String description;
    private int priority;
    private LocalDate deadline;
    private Status status;
    private LocalDate complete;

    public Task() {
    }

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

    /* SETTERS */

    public void setId(int id) {
        this.id = id;
    }

    public void complete() {
        this.status = Status.DONE;
        this.complete = LocalDate.now();
    }

    public void setCaption(String caption) {
        if (caption.isEmpty() || caption.length() > 50) {
            throw new IllegalArgumentException("Task caption length should be from 1 to 50 characters, but is " + caption.length());
        }
        this.caption = caption;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        try {
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("'" + deadline + "' is not a valid date");
        }
    }

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

    public void setStatus(Status status) {
        this.status = status;
    }

    /* GETTERS */

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return "Задача:" + System.lineSeparator() +
                "\tНомер = " + id + System.lineSeparator() +
                "\tЗаголовок: '" + caption + '\'' + System.lineSeparator() +
                "\tОписание: '" + description + '\'' + System.lineSeparator() +
                "\tПриоретет = " + priority + System.lineSeparator() +
                "\tДедлайн: " + deadline + System.lineSeparator() +
                "\tСтатус: " + status + System.lineSeparator() +
                (complete != null ? "\tcomplete: " + complete + System.lineSeparator() : "");
    }
}
