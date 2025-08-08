package easytask;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class in the MVC architecture.
 * Handles interactions between the views and the model.
 * Author: Houde Yu (updated for Activity 03)
 */
public class TaskController {

    private TaskList taskList;
    private TaskListUI listUI;

    /** Constructor: initializes data and opens the task list UI. */
    public TaskController() {
        taskList = new TaskList(); // auto-load persisted data
        if (taskList.getAllTasks().isEmpty()) {
            preloadSampleTasks(); // only seed when no data yet
        }
        listUI = new TaskListUI(this);
        listUI.setVisible(true);
    }

    /** Returns all tasks in the list. */
    public List<Task> getAllTasks() {
        return taskList.getAllTasks();
    }

    /** Show detail view for a selected or new task. */
    public void showTaskDetails(Task task, int rowIndex) {
        new TaskDetailUI(this, task, rowIndex);
    }

    /** Add a new task (no uniqueness check). */
    public void addNewTask(Task task) {
        taskList.addTask(task);
    }

    /** Add new task only if title is unique; return true if added. */
    public boolean addNewTaskUnique(Task task) {
        if (task == null || task.getTitle() == null) return false;
        if (taskList.containsTitle(task.getTitle())) return false;
        taskList.addTask(task);
        return true;
    }

    /** Update a task by index. */
    public void updateTask(int index, Task updatedTask) {
        taskList.updateTask(index, updatedTask);
    }

    /** Delete a task by index. */
    public void deleteTask(int index) {
        taskList.removeTask(index);
    }

    /** Get a task by index. */
    public Task getTask(int index) {
        return taskList.getTask(index);
    }

    /** Find a task by its title in O(1) average time. */
    public Task findTaskByTitle(String title) {
        return taskList.getByTitle(title);
    }

    /** Delete a task by title; return true if deleted. */
    public boolean deleteTaskByTitle(String title) {
        return taskList.removeByTitle(title);
    }

    /** Re-display the list UI and refresh table. */
    public void showListUI() {
        listUI.refreshTable();
        listUI.setVisible(true);
    }

    /** Seed sample tasks if storage is empty. */
    private void preloadSampleTasks() {
        taskList.addTask(new SchoolTask("Math HW", "Chapter 5 exercises",
                LocalDate.of(2025, 8, 1), "High", "MATH101"));
        taskList.addTask(new PersonalTask("Meditation", "10 mins in the morning",
                LocalDate.of(2025, 8, 2), "Low", "Wellness"));
    }
}
