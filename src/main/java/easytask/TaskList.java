package easytask;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of Task objects.
 * @author Houde Yu
 */
public class TaskList {

    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    // Add this to support fetching a task by index
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }

    // Replace task at index
    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, updatedTask);
        }
    }

    // Remove task by index
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
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
}
