package easytask;

import java.time.LocalDate;

/**
 * Sends a reminder for a generic task deadline.
 * Not related to Task class hierarchy.
 * Implements Notifiable interface.
 * Author: Houde Yu
 */
public class TaskReminder implements Notifiable {
    private String taskName;
    private LocalDate dueDate;

    public TaskReminder(String taskName, LocalDate dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    @Override
    public String sendNotification() {
        return "⏰ Reminder: Task \"" + taskName + "\" is due on " + dueDate + "!";
    }
}
