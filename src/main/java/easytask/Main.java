package easytask;

import java.time.LocalDate;

/**
 * Simple CLI launcher to test Task and TaskList.
 */
public class Main {
    public static void main(String[] args) {
        TaskList taskList = new TaskList();

        // Add sample tasks
        Task t1 = new Task("Finish IST411 report", "Include scenario and model classes",
                LocalDate.of(2025, 7, 31), "High");

        Task t2 = new Task("Buy groceries", "Milk, bread, eggs",
                LocalDate.of(2025, 8, 1), "Low");

        taskList.addTask(t1);
        taskList.addTask(t2);

        // Display all tasks
        System.out.println("📝 Your Tasks:");
        taskList.showAllTasks();
    }
}
