package easytask;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Represents a task for school assignments.
 * @author Houde Yu
 */
public class SchoolTask extends Task implements Serializable {
    private String courseCode;

    /**
     * Constructor for SchoolTask
     */
    public SchoolTask(String title, String description, LocalDate dueDate, String priority, String courseCode) {
        super(title, description, dueDate, priority);
        this.courseCode = courseCode;
    }

    // Getter for courseCode
    public String getCourseCode() {
        return courseCode;
    }

    // Setter if needed (optional)
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Returns formatted task details.
     */
    @Override
    public String getDetails() {
        return "[School] " + title + " (" + courseCode + "), Due: " + dueDate + ", Priority: " + priority;
    }
}
