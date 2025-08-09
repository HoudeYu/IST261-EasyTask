package easytask;

import javax.swing.*;
import java.awt.*;

/**
 * TaskListUI displays a table of tasks and buttons for interaction.
 * This is the main list view in the List-Detail user interface pattern.
 * Author: Houde Yu (updated for Activity 03 & 04)
 */
public class TaskListUI extends JFrame {

    private final TaskController controller;
    private final JTable taskTable;
    private final TaskTableModel tableModel;

    /** Constructor: initializes UI with task list table and control buttons */
    public TaskListUI(TaskController controller) {
        this.controller = controller;
        this.tableModel = new TaskTableModel(controller.getAllTasks());
        this.taskTable = new JTable(tableModel);

        setTitle("EasyTask - Task List");
        setSize(800, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        initComponents();
        setVisible(true);
    }

    /** Initializes all UI components and layout */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Center: table
        JScrollPane scrollPane = new JScrollPane(taskTable);
        taskTable.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // South: buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton detailsButton        = new JButton("Show Details");
        JButton addButton            = new JButton("Add Task");
        JButton deleteButton         = new JButton("Delete Task");
        JButton findByTitleButton    = new JButton("Find by Title");       // Activity 03
        JButton deleteByTitleButton  = new JButton("Delete by Title");     // Activity 03
        JButton showUrgentButton     = new JButton("Show Most Urgent");    // Activity 04
        JButton popUrgentButton      = new JButton("Pop Most Urgent");     // Activity 04
        JButton quitButton           = new JButton("Quit");

        buttonPanel.add(detailsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(findByTitleButton);
        buttonPanel.add(deleteByTitleButton);
        buttonPanel.add(showUrgentButton);
        buttonPanel.add(popUrgentButton);
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // ===== Listeners =====

        // Open detail window for selected row
        detailsButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                Task task = tableModel.getTaskAt(selectedRow);
                controller.showTaskDetails(task, selectedRow);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task first.",
                        "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add new task
        addButton.addActionListener(e -> {
            controller.showTaskDetails(null, -1);
            setVisible(false);
        });

        // Delete selected row
        deleteButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                controller.deleteTask(selectedRow);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.",
                        "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Activity 03: Find by title (O(1) via HashMap index)
        findByTitleButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Enter title to search:");
            if (title != null && !title.isBlank()) {
                Task t = controller.findTaskByTitle(title.trim());
                if (t == null) {
                    JOptionPane.showMessageDialog(this, "Not found.",
                            "Search", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int idx = controller.getAllTasks().indexOf(t);
                    controller.showTaskDetails(t, idx);
                    setVisible(false);
                }
            }
        });

        // Activity 03: Delete by title (O(1) lookup + remove)
        deleteByTitleButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Enter title to delete:");
            if (title != null && !title.isBlank()) {
                boolean ok = controller.deleteTaskByTitle(title.trim());
                JOptionPane.showMessageDialog(this,
                        ok ? "Deleted." : "Not found.",
                        "Delete by Title",
                        ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                refreshTable();
            }
        });

        // Activity 04: Show (peek) most urgent task (PriorityQueue)
        showUrgentButton.addActionListener(e -> {
            Task t = controller.peekMostUrgentTask();
            if (t == null) {
                JOptionPane.showMessageDialog(this, "No tasks in queue.",
                        "Most Urgent", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String msg = String.format(
                        "Title: %s%nDue: %s%nPriority: %s%nDesc: %s",
                        t.getTitle(),
                        (t.getDueDate() == null ? "N/A" : t.getDueDate().toString()),
                        (t.getPriority() == null ? "N/A" : t.getPriority()),
                        (t.getDescription() == null ? "" : t.getDescription())
                );
                JOptionPane.showMessageDialog(this, msg,
                        "Most Urgent (Peek)", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Activity 04: Pop (remove) most urgent task (PriorityQueue + list sync)
        popUrgentButton.addActionListener(e -> {
            Task t = controller.popMostUrgentTask();
            if (t == null) {
                JOptionPane.showMessageDialog(this, "No tasks to pop.",
                        "Pop Most Urgent", JOptionPane.WARNING_MESSAGE);
            } else {
                String msg = String.format(
                        "Popped: %s (due %s, prio %s)",
                        t.getTitle(),
                        (t.getDueDate() == null ? "N/A" : t.getDueDate().toString()),
                        (t.getPriority() == null ? "N/A" : t.getPriority())
                );
                JOptionPane.showMessageDialog(this, msg,
                        "Pop Most Urgent", JOptionPane.INFORMATION_MESSAGE);
                refreshTable(); // reflect removal in table
            }
        });

        // Exit
        quitButton.addActionListener(e -> System.exit(0));
    }

    /** Refreshes the table data */
    public void refreshTable() {
        tableModel.setTasks(controller.getAllTasks());
    }
}