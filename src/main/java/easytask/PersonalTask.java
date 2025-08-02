package easytask;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * @author Houde Yu
 * Represents a personal life task.
 */
public class PersonalTask extends Task implements Serializable {
    private String moodTag;

    public PersonalTask(String title, String description, LocalDate dueDate, String priority, String moodTag) {
        super(title, description, dueDate, priority);
        this.moodTag = moodTag;
    }

    public String getMoodTag() { return moodTag; }
    public void setMoodTag(String moodTag) { this.moodTag = moodTag; }

    @Override
    public String getDetails() {
        return "[Personal] " + title + " (" + moodTag + "), Due: " + dueDate + ", Priority: " + priority;
    }
}
