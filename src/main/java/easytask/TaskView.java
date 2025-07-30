package easytask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TaskView is the GUI component in the MVC pattern.
 * It displays fields and buttons for task interaction.
 * It does NOT interact with the model directly.
 * @author Houde Yu
 */
public class TaskView extends JFrame {

    // Swing components
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField dueDateField;
    private JTextField priorityField;

    private JButton prevButton;
    private JButton nextButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton quitButton;

    /**
     * Constructor to initialize the TaskView window.
     */
    public TaskView() {
        setTitle("EasyTask - Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // ==== Fields Panel ====
        JPanel fieldPanel = new JPanel(new GridLayout(4, 2));

        fieldPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        fieldPanel.add(titleField);

        fieldPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        fieldPanel.add(descriptionField);

        fieldPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        dueDateField = new JTextField();
        fieldPanel.add(dueDateField);

        fieldPanel.add(new JLabel("Priority (High/Medium/Low):"));
        priorityField = new JTextField();
        fieldPanel.add(priorityField);

        add(fieldPanel, BorderLayout.CENTER);

        // ==== Buttons Panel ====
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        quitButton = new JButton("Quit");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Final display
        setVisible(true);
    }

    // ==== Accessors for input fields ====

    public String getTitleField() {
        return titleField.getText();
    }

    public String getDescriptionField() {
        return descriptionField.getText();
    }

    public String getDueDateField() {
        return dueDateField.getText();
    }

    public String getPriorityField() {
        return priorityField.getText();
    }

    // ==== Setters for updating fields ====

    public void setTitleField(String text) {
        titleField.setText(text);
    }

    public void setDescriptionField(String text) {
        descriptionField.setText(text);
    }

    public void setDueDateField(String text) {
        dueDateField.setText(text);
    }

    public void setPriorityField(String text) {
        priorityField.setText(text);
    }

    // ==== Button Listener Setters ====

    public void addPrevListener(ActionListener listener) {
        prevButton.addActionListener(listener);
    }

    public void addNextListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addQuitListener(ActionListener listener) {
        quitButton.addActionListener(listener);
    }

    /**
     * Shows a message to the user (e.g. errors, confirmations).
     * @param message the message to show
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
