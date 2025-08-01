// ✅ TaskListUI.java
package easytask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TaskListUI displays a table of tasks and buttons for interaction.
 * This is the main list view in the List-Detail user interface pattern.
 * Author: Houde Yu
 */
public class TaskListUI extends JFrame {

    private final TaskController controller;
    private final JTable taskTable;
    private final TaskTableModel tableModel;

    /**
     * Constructor: initializes UI with task list table and control buttons
     * @param controller The controller that handles data and view transitions
     */
    public TaskListUI(TaskController controller) {
        this.controller = controller;
        this.tableModel = new TaskTableModel(controller.getAllTasks());
        this.taskTable = new JTable(tableModel);

        setTitle("EasyTask - Task List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        initComponents();
        setVisible(true);
    }

    /**
     * Initializes all UI components and layout
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(taskTable);
        taskTable.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton detailsButton = new JButton("Show Details");
        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton quitButton = new JButton("Quit");

        buttonPanel.add(detailsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        // Action Listeners
        detailsButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                Task task = tableModel.getTaskAt(selectedRow);
                controller.showTaskDetails(task, selectedRow);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task first.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        addButton.addActionListener(e -> {
            controller.showTaskDetails(null, -1);
            setVisible(false);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow >= 0) {
                controller.deleteTask(selectedRow);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        quitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Refreshes the table data
     */
    public void refreshTable() {
        tableModel.setTasks(controller.getAllTasks());
    }
}
