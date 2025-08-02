package easytask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of Task objects with persistence support.
 * @author Houde Yu
 */
public class TaskList {

    private List<Task> tasks;
    private final String TASK_FILE = "tasks.ser";

    public TaskList() {
        this.tasks = new ArrayList<>();
        loadFromFile(); // 加载已保存的任务
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveToFile(); // 添加后保存
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        saveToFile(); // 删除后保存
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

    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, updatedTask);
            saveToFile(); // 修改后保存
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveToFile(); // 删除后保存
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

    /**
     * Save current task list to file using Java object serialization.
     */
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TASK_FILE))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Load task list from file if it exists.
     */
    public void loadFromFile() {
        File file = new File(TASK_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                tasks = (List<Task>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
            }
        } else {
            tasks = new ArrayList<>(); // 如果文件不存在，初始化空列表
        }
    }
}
