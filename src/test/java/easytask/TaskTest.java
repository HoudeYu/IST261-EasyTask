package easytask;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testConstructorAndGetters() {
        Task task = new Task("Write report", "Complete M03", LocalDate.of(2025, 7, 30), "High");

        assertEquals("Write report", task.getTitle());
        assertEquals("Complete M03", task.getDescription());
        assertEquals(LocalDate.of(2025, 7, 30), task.getDueDate());
        assertEquals("High", task.getPriority());
        assertFalse(task.isCompleted());
    }

    @Test
    void testSetters() {
        Task task = new Task("Initial", "Desc", LocalDate.now(), "Low");

        task.setTitle("Updated title");
        task.setDescription("Updated desc");
        task.setDueDate(LocalDate.of(2025, 8, 1));
        task.setPriority("Medium");
        task.setCompleted(true);

        assertEquals("Updated title", task.getTitle());
        assertEquals("Updated desc", task.getDescription());
        assertEquals(LocalDate.of(2025, 8, 1), task.getDueDate());
        assertEquals("Medium", task.getPriority());
        assertTrue(task.isCompleted());
    }

    @Test
    void testToString() {
        Task task = new Task("Read book", "Ch.1-3", LocalDate.of(2025, 8, 2), "Low");
        String output = task.toString();
        assertTrue(output.contains("Read book"));
        assertTrue(output.contains("Low"));
        assertTrue(output.contains("❌"));
    }
}
