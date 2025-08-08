package easytask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TaskDetailUI displays and edits a single task in the List-Detail UI pattern.
 * Supports updating or deleting a task and returning to the main list.
 *
 * Author: Houde Yu (updated optional uniqueness check)
 */
public class TaskDetailUI extends JFrame {

    private TaskController controller;
    private Task task; // can be null if it's a new task
    private int taskIndex; // -1 means new task

    // UI Components
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField dueDateField;
    private JTextField priorityField;
    private JTextField tagField;

    private JButton saveButton;
    private JButton deleteButton;
    private JButton cancelButton;

    public TaskDetailUI(TaskController controller, Task task, int index) {
        this.controller = controller;
        this.task = task;
        this.taskIndex = index;

        initComponents();
        populateFields();

        setTitle("Task Detail");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Due Date (YYYY-MM-DD):"));
        dueDateField = new JTextField();
        add(dueDateField);

        add(new JLabel("Priority:"));
        priorityField = new JTextField();
        add(priorityField);

        add(new JLabel("Tag (Course or Mood):"));
        tagField = new JTextField();
        add(tagField);

        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        add(saveButton);
        add(deleteButton);
        add(cancelButton);

        saveButton.addActionListener(new SaveButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        cancelButton.addActionListener(e -> {
            dispose();
            controller.showListUI();
        });
    }

    private void populateFields() {
        if (task != null) {
            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());
            dueDateField.setText(task.getDueDate().toString());
            priorityField.setText(task.getPriority());

            if (task instanceof SchoolTask) {
                tagField.setText(((SchoolTask) task).getCourseCode());
            } else if (task instanceof PersonalTask) {
                tagField.setText(((PersonalTask) task).getMoodTag());
            } else {
                tagField.setText("");
            }
        } else {
            titleField.setText("");
            descriptionField.setText("");
            dueDateField.setText("");
            priorityField.setText("");
            tagField.setText("");
        }
    }

    private Task getTaskFromInput() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String dueDate = dueDateField.getText();
        String priority = priorityField.getText();
        String tag = tagField.getText();

        if (title.toLowerCase().contains("course") || tag.matches("[A-Z]{2,}[0-9]{2,}")) {
            return new SchoolTask(title, description, java.time.LocalDate.parse(dueDate), priority, tag);
        } else {
            return new PersonalTask(title, description, java.time.LocalDate.parse(dueDate), priority, tag);
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Task updated = getTaskFromInput();
                if (taskIndex >= 0) {
                    controller.updateTask(taskIndex, updated);
                } else {
                    // OPTIONAL: enforce unique titles via HashMap index
                    boolean ok = controller.addNewTaskUnique(updated);
                    if (!ok) {
                        JOptionPane.showMessageDialog(TaskDetailUI.this,
                                "Title already exists. Choose another title.",
                                "Duplicate Title", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                controller.showListUI();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(TaskDetailUI.this,
                        "Error saving task: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (taskIndex >= 0) {
                controller.deleteTask(taskIndex);
            }
            controller.showListUI();
            dispose();
        }
    }
}
