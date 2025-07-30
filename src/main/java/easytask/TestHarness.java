package easytask;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Manual unit tests for Task class hierarchy and polymorphism.
 * @author Houde Yu
 */
public class TestHarness {

    // Constructor
    public TestHarness() {
        System.out.println("🧪 Starting manual tests...");
    }

    /**
     * Test method for inheritance and polymorphism.
     */
    public void testClassHierarchy() {
        System.out.println("\n--- Testing Class Hierarchy with Inheritance ---");

        ArrayList<Task> tasks = new ArrayList<>();

        // Create sub-class objects
        Task schoolTask = new SchoolTask("Finish Essay", "Write a paper on Java Inheritance",
                LocalDate.of(2025, 8, 5), "High", "IST411");

        Task personalTask = new PersonalTask("Go Jogging", "Run 5km in the park",
                LocalDate.of(2025, 8, 2), "Medium", "Health");

        // Add to list
        tasks.add(schoolTask);
        tasks.add(personalTask);

        // Polymorphic loop
        for (Task task : tasks) {
            System.out.println(task.getDetails());
        }
    }

    /**
     * Tests the use of the Notifiable interface and polymorphism with unrelated classes.
     * Author: Houde Yu
     */
    public void testInterface() {
        System.out.println("\n--- Testing Notifiable Interface with Multiple Classes ---");

        ArrayList<Notifiable> notifications = new ArrayList<>();

        // Create objects that implement Notifiable
        Notifiable reminder = new TaskReminder("Submit report", LocalDate.of(2025, 8, 3));
        Notifiable quote = new MotivationalQuote("Success is the sum of small efforts, repeated daily.");

        notifications.add(reminder);
        notifications.add(quote);

        // Polymorphic loop
        for (Notifiable item : notifications) {
            System.out.println(item.sendNotification());
        }
    }

}
