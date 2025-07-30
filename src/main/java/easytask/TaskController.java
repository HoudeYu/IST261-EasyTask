package easytask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller class in the MVC architecture.
 * It handles all user interactions between the TaskView (GUI) and TaskList (Model).
 * Author: Houde Yu
 */
public class TaskController {

    private TaskView view;
    private TaskList model;
    private int currentIndex = 0; // Index of the currently displayed task

    /**
     * Constructor connects model and view, sets up listeners.
     */
    public TaskController() {
        this.model = new TaskList();
        this.view = new TaskView();

        // Preload some tasks (optional)
        preloadSampleTasks();

        // Setup listeners
        view.addPrevListener(new PrevListener());
        view.addNextListener(new NextListener());
        view.addAddListener(new AddListener());
        view.addUpdateListener(new UpdateListener());
        view.addDeleteListener(new DeleteListener());
        view.addQuitListener(new QuitListener());

        // Show first task if available
        if (!model.getAllTasks().isEmpty()) {
            showTask(currentIndex);
        }
    }

    /**
     * Displays a task in the view based on index.
     * @param index Task index in the list
     */
    private void showTask(int index) {
        List<Task> tasks = model.getAllTasks();
        if (tasks.isEmpty() || index < 0 || index >= tasks.size()) {
            view.showMessage("No task to show.");
            return;
        }

        Task task = tasks.get(index);
        view.setTitleField(task.getTitle());
        view.setDescriptionField(task.getDescription());
        view.setDueDateField(task.getDueDate().toString());
        view.setPriorityField(task.getPriority());
    }

    /**
     * Reads task data from the view and returns a Task object.
     * @return Task object
     */
    private Task getTaskFromView() {
        String title = view.getTitleField();
        String description = view.getDescriptionField();
        LocalDate dueDate = LocalDate.parse(view.getDueDateField());
        String priority = view.getPriorityField();
        return new PersonalTask(title, description, dueDate, priority, "Default"); // Default moodTag
    }

    /**
     * Adds some initial sample tasks for testing.
     */
    private void preloadSampleTasks() {
        model.addTask(new SchoolTask("Math HW", "Chapter 5 exercises", LocalDate.of(2025, 8, 1), "High", "MATH101"));
        model.addTask(new PersonalTask("Meditation", "10 mins in the morning", LocalDate.of(2025, 8, 2), "Low", "Wellness"));
    }

    // ===== Listeners for buttons =====

    private class PrevListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentIndex > 0) {
                currentIndex--;
                showTask(currentIndex);
            } else {
                view.showMessage("This is the first task.");
            }
        }
    }

    private class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentIndex < model.getAllTasks().size() - 1) {
                currentIndex++;
                showTask(currentIndex);
            } else {
                view.showMessage("This is the last task.");
            }
        }
    }

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Task newTask = getTaskFromView();
                model.addTask(newTask);
                currentIndex = model.getAllTasks().size() - 1;
                view.showMessage("Task added successfully.");
            } catch (Exception ex) {
                view.showMessage("Error adding task: " + ex.getMessage());
            }
        }
    }

    private class UpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (model.getAllTasks().isEmpty()) {
                    view.showMessage("No task to update.");
                    return;
                }

                Task updatedTask = getTaskFromView();
                model.getAllTasks().set(currentIndex, updatedTask);
                view.showMessage("Task updated.");
            } catch (Exception ex) {
                view.showMessage("Error updating task: " + ex.getMessage());
            }
        }
    }

    private class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.getAllTasks().isEmpty()) {
                view.showMessage("No task to delete.");
                return;
            }

            model.getAllTasks().remove(currentIndex);

            if (currentIndex > 0) {
                currentIndex--;
            }

            if (!model.getAllTasks().isEmpty()) {
                showTask(currentIndex);
            } else {
                view.setTitleField("");
                view.setDescriptionField("");
                view.setDueDateField("");
                view.setPriorityField("");
            }

            view.showMessage("Task deleted.");
        }
    }

    private class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
