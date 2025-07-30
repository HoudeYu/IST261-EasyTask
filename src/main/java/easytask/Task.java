package easytask;

import java.time.LocalDate;

/**
 * @author Houde Yu
 * Abstract parent class for all tasks.
 */
public abstract class Task {
    protected String title;
    protected String description;
    protected LocalDate dueDate;
    protected String priority;
    protected boolean completed;

    public Task(String title, String description, LocalDate dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and Setters...

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

    /**
     * Abstract method to be overridden by sub-classes.
     * @return Detailed string for the task
     */
    public abstract String getDetails();
}
