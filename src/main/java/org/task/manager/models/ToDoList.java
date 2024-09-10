package org.task.manager.models;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ToDoList {
    private static class MyLinkedList {
        private static class Node {
            Task task;
            Node next;
            Node prev;

            public Node(Task task) {
                this.task = task;
                next = null;
            }
        }

        private Node head;
        private Node tail;

        public MyLinkedList() {
            head = null;
        }

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

        public Node getHead() {
            return head;
        }

        public Stream<Task> stream() {
            Iterator<Task> sourceIterator = new Iterator<>() {
                Node current = tail;

                /**
                 * Returns {@code true} if the iteration has more elements.
                 * (In other words, returns {@code true} if {@link #next} would
                 * return an element rather than throwing an exception.)
                 *
                 * @return {@code true} if the iteration has more elements
                 */
                @Override
                public boolean hasNext() {
                    return current != null;
                }

                /**
                 * Returns the next element in the iteration.
                 *
                 * @return the next element in the iteration
                 * @throws NoSuchElementException if the iteration has no more elements
                 */
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

    private final MyLinkedList list;
    private final HashMap<Integer, MyLinkedList.Node> map;
    private int maxId;
    private int size;

    public ToDoList() {
        list = new MyLinkedList();
        map = new HashMap<>();
        maxId = 0;
    }

    public void add(Task task, int id) {
        if (map.containsKey(id)) {
            throw new IllegalArgumentException("Two tasks with the same id");
        }
        maxId = Math.max(maxId, id);
        list.add(task);
        map.put(id, list.getHead());
        size++;
    }

    public void add(Task task) {
        task.setId(++maxId);
        add(task, maxId);
    }

    public Task get(int id) {
        return map.get(id).task;
    }

    public void remove(int id) {
        MyLinkedList.Node node = map.get(id);
        if (node == null) {
            throw new NoSuchElementException();
        }
        list.remove(node);
        map.remove(id);
        size--;
    }

    public Stream<Task> stream() {
        return list.stream();
    }

    public int size() {
        return size;
    }
}
