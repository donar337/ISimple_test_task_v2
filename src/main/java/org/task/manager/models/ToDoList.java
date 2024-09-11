package org.task.manager.models;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The {@code ToDoList} class represents a custom task management system
 * using a doubly linked list to store tasks and a hash map to access them by their unique id.
 * This allows for efficient addition and removal of tasks.
 */
public class ToDoList {

    /**
     * Inner static class representing a custom doubly linked list.
     * Stores tasks and allows traversal in both directions.
     */
    private static class MyLinkedList {

        /**
         * Inner static class representing a node in the doubly linked list.
         * Each node holds a reference to a task and to the next and previous nodes.
         */
        private static class Node {
            Task task;
            Node next;
            Node prev;

            /**
             * Constructor to create a new node with the specified task.
             *
             * @param task the task to be stored in the node.
             */
            public Node(Task task) {
                this.task = task;
                next = null;
                prev = null;
            }
        }

        /** The head (first node) of the linked list. */
        private Node head;

        /** The tail (last node) of the linked list. */
        private Node tail;

        /**
         * Constructor to initialize an empty linked list.
         */
        public MyLinkedList() {
            head = null;
            tail = null;
        }

        /**
         * Adds a new task to the beginning of the linked list.
         *
         * @param task the task to be added.
         */
        public void add(Task task) {
            Node node = new Node(task);
            node.next = head;
            if (head != null) {
                head.prev = node;
            }
            this.head = node;
            if (tail == null) {
                this.tail = node;
            }
        }

        /**
         * Removes a specified node from the linked list.
         *
         * @param node the node to be removed.
         */
        public void remove(Node node) {
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (head == node) {
                head = head.next;
            }
            if (tail == node) {
                tail = tail.prev;
            }
        }

        /**
         * Returns the head (first node) of the linked list.
         *
         * @return the head node.
         */
        public Node getHead() {
            return head;
        }

        /**
         * Returns a stream of tasks starting from the tail (least recently added task).
         *
         * @return a stream of tasks.
         */
        public Stream<Task> stream() {
            Iterator<Task> sourceIterator = new Iterator<>() {
                Node current = tail;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public Task next() {
                    Task task = current.task;
                    current = current.prev;
                    return task;
                }
            };
            Iterable<Task> iterable = () -> sourceIterator;
            return StreamSupport.stream(iterable.spliterator(), false);
        }
    }

    /** A custom linked list to store tasks in the to-do list. */
    private final MyLinkedList list;

    /** A hash map to store tasks by their unique id for quick access. */
    private final HashMap<Integer, MyLinkedList.Node> map;

    /** The highest task id assigned so far. */
    private int maxId;

    /** The current number of tasks in the to-do list. */
    private int size;

    /**
     * Constructor to initialize an empty to-do list.
     */
    public ToDoList() {
        list = new MyLinkedList();
        map = new HashMap<>();
        maxId = 0;
        size = 0;
    }

    /**
     * Adds a task to the to-do list with a specified id.
     *
     * @param task the task to be added.
     * @param id the unique id for the task.
     * @throws IllegalArgumentException if a task with the same id already exists.
     */
    public void add(Task task, int id) {
        if (map.containsKey(id)) {
            throw new IllegalArgumentException("Two tasks with the same id");
        }
        maxId = Math.max(maxId, id);
        list.add(task);
        map.put(id, list.getHead());
        size++;
    }

    /**
     * Adds a task to the to-do list with an automatically generated unique id.
     *
     * @param task the task to be added.
     */
    public void add(Task task) {
        task.setId(++maxId);
        add(task, maxId);
    }

    /**
     * Retrieves a task by its id.
     *
     * @param id the unique identifier of the task.
     * @return the task associated with the specified id.
     * @throws NoSuchElementException if no task with the given id exists.
     */
    public Task get(int id) {
        if (!map.containsKey(id)) {
            throw new NoSuchElementException("Task with id " + id + " not found");
        }
        return map.get(id).task;
    }

    /**
     * Removes a task by its id from the to-do list.
     *
     * @param id the unique identifier of the task.
     * @throws NoSuchElementException if no task with the given id exists.
     */
    public void remove(int id) {
        MyLinkedList.Node node = map.get(id);
        if (node == null) {
            throw new NoSuchElementException();
        }
        list.remove(node);
        map.remove(id);
        size--;
    }

    /**
     * Returns a stream of tasks in the to-do list.
     *
     * @return a stream of tasks starting from the tail (least recently added task).
     */
    public Stream<Task> stream() {
        return list.stream();
    }

    /**
     * Returns the number of tasks in the to-do list.
     *
     * @return the current size of the to-do list.
     */
    public int size() {
        return size;
    }
}
