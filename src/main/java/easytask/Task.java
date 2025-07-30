package easytask;

import java.time.LocalDate;

/**
 * Represents a single task in the EasyTask application.
 */
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority; // "High", "Medium", "Low"
    private boolean completed;

    public Task(String title, String description, LocalDate dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return "[" + priority + "] " + title + " (Due: " + dueDate + ")"
                + (completed ? " ✅" : " ❌");
    }
}
