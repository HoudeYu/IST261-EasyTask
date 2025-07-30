package easytask;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test
    void testAddAndGetAllTasks() {
        TaskList taskList = new TaskList();
        Task t1 = new Task("Task1", "Desc", LocalDate.of(2025, 7, 30), "High");
        Task t2 = new Task("Task2", "Desc", LocalDate.of(2025, 8, 1), "Low");

        taskList.addTask(t1);
        taskList.addTask(t2);

        List<Task> allTasks = taskList.getAllTasks();
        assertEquals(2, allTasks.size());
        assertEquals(t1, allTasks.get(0));
        assertEquals(t2, allTasks.get(1));
    }

    @Test
    void testRemoveTask() {
        TaskList taskList = new TaskList();
        Task t1 = new Task("Task1", "Desc", LocalDate.of(2025, 7, 30), "High");

        taskList.addTask(t1);
        assertEquals(1, taskList.getAllTasks().size());

        taskList.removeTask(t1);
        assertEquals(0, taskList.getAllTasks().size());
    }
}
