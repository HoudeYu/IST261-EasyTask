package easytask;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class in the MVC architecture.
 * Handles interactions between the views and the model.
 * Author: Houde Yu
 */
public class TaskController {

    private TaskList taskList;
    private TaskListUI listUI;

    /**
     * Constructor: initializes data and opens the task list UI.
     */
    public TaskController() {
        taskList = new TaskList();
        preloadSampleTasks(); // Preload initial tasks for testing
        listUI = new TaskListUI(this);
        listUI.setVisible(true);
    }

    /**
     * Returns all tasks in the list.
     * @return a List of Task objects
     */
    public List<Task> getAllTasks() {
        return taskList.getAllTasks();
    }

    /**
     * Called when a task row is selected for detail view.
     * @param task the task to show/edit
     * @param rowIndex index of the task in the list; -1 means a new task
     */
    public void showTaskDetails(Task task, int rowIndex) {
        new TaskDetailUI(this, task, rowIndex);
    }

    /**
     * Adds a new task to the list.
     * Called when a detail view saves a task with rowIndex == -1.
     * @param task new task to add
     */
    public void addNewTask(Task task) {
        taskList.addTask(task);
    }

    /**
     * Updates a task at the given index.
     * Called when detail view edits an existing task.
     * @param index position in list
     * @param updatedTask modified task object
     */
    public void updateTask(int index, Task updatedTask) {
        taskList.updateTask(index, updatedTask);
    }

    /**
     * Deletes a task at the specified index.
     * @param index index of task to delete
     */
    public void deleteTask(int index) {
        taskList.removeTask(index);
    }

    /**
     * Gets a task by index.
     * @param index position in list
     * @return Task object
     */
    public Task getTask(int index) {
        return taskList.getAllTasks().get(index);
    }

    /**
     * Re-displays the TaskListUI and refreshes the table.
     * Called after returning from TaskDetailUI.
     */
    public void showListUI() {
        listUI.refreshTable();
        listUI.setVisible(true);
    }

    /**
     * Adds some dummy data for initial display.
     */
    private void preloadSampleTasks() {
        taskList.addTask(new SchoolTask("Math HW", "Chapter 5 exercises",
                LocalDate.of(2025, 8, 1), "High", "MATH101"));
        taskList.addTask(new PersonalTask("Meditation", "10 mins in the morning",
                LocalDate.of(2025, 8, 2), "Low", "Wellness"));
    }
}
