package easytask;

import java.time.LocalDate;

/**
 * @author Houde Yu
 * Represents a task for school assignments.
 */
public class SchoolTask extends Task {
    private String courseName;

    public SchoolTask(String title, String description, LocalDate dueDate, String priority, String courseName) {
        super(title, description, dueDate, priority);
        this.courseName = courseName;
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @Override
    public String getDetails() {
        return "[School] " + title + " for course: " + courseName + ", Due: " + dueDate + ", Priority: " + priority;
    }
}
