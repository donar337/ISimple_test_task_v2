package org.task.manager.models;

import org.task.manager.exceptions.TaskCreationException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class Task {
    private int id;
    private String caption;
    private String description;
    private int priority;
    private LocalDate deadline;
    private Status status;
    private LocalDate complete;
    private boolean changed;

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
    ) throws TaskCreationException, NullPointerException {
        try {
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new TaskCreationException("Task id must be an integer, got '" + id + "'");
        }

        setCaption(caption);

        this.description = description;

        setPriority(priority);

        this.deadline = LocalDate.parse(deadline);
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TaskCreationException("Not a valid status '" + status.toUpperCase() + "'");
        }
        if (complete.isEmpty()) {
            this.complete = null;
        } else {
            this.complete = LocalDate.parse(complete);
        }
        this.changed = false;
    }

    /* SETTERS */

    public void setId(int id) {
        this.id = id;
        changed = true;
    }

    public void setStatus(Status status) {
        this.status = status;
        changed = true;
    }

    public void setComplete(LocalDate complete) {
        this.complete = complete;
        changed = true;
    }

    public void setCaption(String caption) {
        if (caption.isEmpty() || caption.length() > 50) {
            throw new TaskCreationException("Task caption length should be from 1 to 50 characters, but is " + caption.length());
        }
        this.caption = caption;
        changed = true;
    }

    public void setDescription(String description) {
        this.description = description;
        changed = true;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        changed = true;
    }

    public void setPriority(String priority) {
        try {
            int priorityInt = Integer.parseInt(priority);
            if (priorityInt < 0 || priorityInt > 10) {
                throw new TaskCreationException("Task priority should be from 0 to 10, but is " + priority);
            }
            this.priority = priorityInt;
        } catch (NumberFormatException e) {
            throw new TaskCreationException("Task priority should be an integer, but is '" + priority + "'");
        }
        changed = true;
    }

    /* GETTERS */

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
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
