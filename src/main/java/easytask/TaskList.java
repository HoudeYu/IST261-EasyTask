package easytask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Manages a collection of Task objects with persistence support.
 * Adds a transient HashMap<String, Task> index for O(1) average-time lookup by title.
 * Author: Houde Yu (updated for Activity 03)
 */
public class TaskList {

    private List<Task> tasks;
    private final String TASK_FILE = "tasks.ser";

    // O(1) lookup by title; transient so it is NOT serialized
    private transient Map<String, Task> indexByTitle = new HashMap<>();

    public TaskList() {
        this.tasks = new ArrayList<>();
        loadFromFile();     // load saved tasks if any
        rebuildIndex();     // build the title index for fast lookup
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

    /** Add a task and maintain the title index. */
    public void addTask(Task task) {
        tasks.add(task);
        if (task != null && task.getTitle() != null) {
            indexByTitle.put(task.getTitle(), task);
        }
        saveToFile();
    }

    /** Remove a specific task object and maintain the title index. */
    public void removeTask(Task task) {
        if (task != null && task.getTitle() != null) {
            indexByTitle.remove(task.getTitle());
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

    /** Update a task at index and maintain the title index (handles title changes). */
    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            Task old = tasks.get(index);
            String oldTitle = (old == null) ? null : old.getTitle();

            tasks.set(index, updatedTask);

            if (oldTitle != null) indexByTitle.remove(oldTitle);
            if (updatedTask != null && updatedTask.getTitle() != null) {
                indexByTitle.put(updatedTask.getTitle(), updatedTask);
            }
            saveToFile();
        }
    }

    /** Remove by index and maintain the title index. */
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task old = tasks.get(index);
            if (old != null && old.getTitle() != null) {
                indexByTitle.remove(old.getTitle());
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

    /** Save current task list to file using Java object serialization. */
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TASK_FILE))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /** Load task list from file if it exists, then rebuild the title index. */
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
        // keep index consistent with current tasks
        rebuildIndex();
    }
}
