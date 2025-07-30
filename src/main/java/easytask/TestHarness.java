package easytask;

import java.time.LocalDate;

/**
 * Manual unit tests for Task and TaskList classes.
 */
public class TestHarness {

    public TestHarness() {
        System.out.println("🧪 Starting manual tests...");

        testTaskClass();
        testTaskListClass();

        System.out.println("✅ All manual tests completed.");
    }

    private void testTaskClass() {
        System.out.println("\n--- Testing Task Class ---");

        // Create a Task
        Task task = new Task("Write report", "Finish M03-A01 hand test",
                LocalDate.of(2025, 7, 30), "High");

        // Test getters
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Priority: " + task.getPriority());
        System.out.println("Completed: " + task.isCompleted());

        // Test setters
        task.setTitle("Write final report");
        task.setDescription("Finish M03-A01 + polish");
        task.setDueDate(LocalDate.of(2025, 8, 1));
        task.setPriority("Medium");
        task.setCompleted(true);

        // Print updated task
        System.out.println("Updated Task: " + task.toString());
    }

    private void testTaskListClass() {
        System.out.println("\n--- Testing TaskList Class ---");

        TaskList taskList = new TaskList();

        // Add tasks
        Task t1 = new Task("Buy milk", "2% fat", LocalDate.of(2025, 7, 31), "Low");
        Task t2 = new Task("Read book", "Ch.5 - MVC", LocalDate.of(2025, 8, 2), "Medium");

        taskList.addTask(t1);
        taskList.addTask(t2);

        // Show all tasks
        System.out.println("All Tasks:");
        taskList.showAllTasks();

        // Remove task and recheck
        taskList.removeTask(t1);
        System.out.println("\nAfter removing one task:");
        taskList.showAllTasks();
    }

    public static void main(String[] args) {
        new TestHarness(); // Run manual test
    }
}
