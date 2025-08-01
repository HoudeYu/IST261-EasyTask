package easytask;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskTableModel is a table model that provides task data for display in a JTable.
 * Author: Houde Yu
 */
public class TaskTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Title", "Description", "Due Date", "Priority"};
    private List<Task> taskList;

    public TaskTableModel(List<Task> tasks) {
        this.taskList = new ArrayList<>(tasks); // create mutable copy
    }

    public void setTasks(List<Task> newList) {
        this.taskList = new ArrayList<>(newList); // replace old list
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return taskList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Task task = taskList.get(row);
        return switch (col) {
            case 0 -> task.getTitle();
            case 1 -> task.getDescription();
            case 2 -> task.getDueDate().toString();
            case 3 -> task.getPriority();
            default -> null;
        };
    }

    public Task getTaskAt(int row) {
        return taskList.get(row);
    }

    public void refresh() {
        fireTableDataChanged();
    }
}
