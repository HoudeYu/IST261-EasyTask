package easytask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Manages a collection of Task objects with persistence support.
 * Adds:
 *  - transient HashMap<String, Task> index for O(1) lookup by title (Activity 03)
 *  - transient PriorityQueue<Task> urgentQueue for "most-urgent" retrieval (Activity 04)
 * Author: Houde Yu (updated for Activity 03 & 04)
 */
public class TaskList {

    private List<Task> tasks;
    private final String TASK_FILE = "tasks.ser";

    // Activity 03: O(1) lookup by title; transient so it is NOT serialized
    private transient Map<String, Task> indexByTitle = new HashMap<>();

    // Activity 04: PriorityQueue ordered by priority then dueDate (most urgent at head)
    private transient PriorityQueue<Task> urgentQueue;

    public TaskList() {
        this.tasks = new ArrayList<>();
        loadFromFile();     // load saved tasks if any
        rebuildIndex();     // build the title index for fast lookup
        rebuildUrgentQueue(); // build the urgent queue for most-urgent feature
    }

    /** Rebuild the title index from the current tasks list. */
    private void rebuildIndex() {
        indexByTitle = new HashMap<>();
        for (Task t : tasks) {
            if (t != null && t.getTitle() != null) {
                indexByTitle.put(t.getTitle(), t);
            }
        }
    }

    /** Rebuild the urgentQueue from the current tasks list. */
    private void rebuildUrgentQueue() {
        urgentQueue = new PriorityQueue<>(urgencyComparator());
        for (Task t : tasks) {
            if (t != null) urgentQueue.offer(t);
        }
    }

    /** Comparator: higher priority first (High > Medium > Low), tie-breaker = earlier due date. */
    private Comparator<Task> urgencyComparator() {
        return (a, b) -> {
            int pa = priorityValue(a == null ? null : a.getPriority());
            int pb = priorityValue(b == null ? null : b.getPriority());
            if (pa != pb) {
                // higher priority value should come first
                return Integer.compare(pb, pa);
            }
            // earlier due date first (null-safe: nulls go last)
            if (a == null || a.getDueDate() == null) return 1;
            if (b == null || b.getDueDate() == null) return -1;
            return a.getDueDate().compareTo(b.getDueDate());
        };
    }

    /** Maps String priority to an int for comparison. Higher number = higher priority. */
    private int priorityValue(String p) {
        if (p == null) return 0;
        String s = p.trim().toLowerCase();
        switch (s) {
            case "high":   return 3;
            case "medium": return 2;
            case "low":    return 1;
            default:       return 0; // unknown -> lowest
        }
    }

    /** Add a task and maintain indexes/queues. */
    public void addTask(Task task) {
        tasks.add(task);
        if (task != null) {
            if (task.getTitle() != null) {
                indexByTitle.put(task.getTitle(), task);
            }
            if (urgentQueue == null) rebuildUrgentQueue();
            urgentQueue.offer(task); // O(log n)
        }
        saveToFile();
    }

    /** Remove a specific task object and maintain indexes/queues. */
    public void removeTask(Task task) {
        if (task != null) {
            if (task.getTitle() != null) {
                indexByTitle.remove(task.getTitle());
            }
            if (urgentQueue != null) urgentQueue.remove(task); // O(n) acceptable for demo
        }
        tasks.remove(task);
        saveToFile();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }

    /** Update a task at index and maintain the title index & urgent queue (handles title/priority/due changes). */
    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            Task old = tasks.get(index);
            String oldTitle = (old == null) ? null : old.getTitle();

            // remove old from structures
            if (oldTitle != null) indexByTitle.remove(oldTitle);
            if (urgentQueue != null && old != null) urgentQueue.remove(old);

            // set new
            tasks.set(index, updatedTask);

            // add new to structures
            if (updatedTask != null) {
                if (updatedTask.getTitle() != null) {
                    indexByTitle.put(updatedTask.getTitle(), updatedTask);
                }
                if (urgentQueue == null) rebuildUrgentQueue();
                urgentQueue.offer(updatedTask);
            }
            saveToFile();
        }
    }

    /** Remove by index and maintain the title index & urgent queue. */
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task old = tasks.get(index);
            if (old != null) {
                if (old.getTitle() != null) {
                    indexByTitle.remove(old.getTitle());
                }
                if (urgentQueue != null) urgentQueue.remove(old);
            }
            tasks.remove(index);
            saveToFile();
        }
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /** O(1) average-time get by title; returns null if not found. */
    public Task getByTitle(String title) {
        if (title == null) return null;
        return indexByTitle.get(title.trim());
    }

    /** Remove by title; returns true if a task was removed. */
    public boolean removeByTitle(String title) {
        if (title == null) return false;
        Task t = indexByTitle.remove(title.trim());
        if (t != null) {
            if (urgentQueue != null) urgentQueue.remove(t);
            tasks.remove(t);
            saveToFile();
            return true;
        }
        return false;
    }

    /** Returns true if a task with the given title exists. */
    public boolean containsTitle(String title) {
        if (title == null) return false;
        return indexByTitle.containsKey(title.trim());
    }

    // ===== Activity 04 API: most-urgent operations =====

    /** See (but not remove) the most urgent task; returns null if none. */
    public Task peekMostUrgent() {
        if (urgentQueue == null) rebuildUrgentQueue();
        return urgentQueue.peek(); // O(1)
    }

    /**
     * Pop (remove) the most urgent task from both the queue and the list.
     * Returns the removed task, or null if empty.
     */
    public Task popMostUrgent() {
        if (urgentQueue == null) rebuildUrgentQueue();
        Task t = urgentQueue.poll(); // O(log n)
        if (t != null) {
            // keep ArrayList and title-index consistent
            tasks.remove(t);
            if (t.getTitle() != null) indexByTitle.remove(t.getTitle());
            saveToFile();
        }
        return t;
    }

    /** Save current task list to file using Java object serialization. */
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TASK_FILE))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /** Load task list from file if it exists, then rebuild indices/queues. */
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(TASK_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                tasks = (List<Task>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
                tasks = new ArrayList<>();
            }
        } else {
            tasks = new ArrayList<>();
        }
        // keep auxiliary structures consistent with current tasks
        rebuildIndex();
        rebuildUrgentQueue();
    }
}
