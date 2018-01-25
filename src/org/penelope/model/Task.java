package org.penelope.model;

import org.penelope.persistence.DAO;

@DAO.Table(tableName = "tasks")
public class Task implements DomainObject {
    @DAO.DBField(isPk = true)
    public int id;

    @Override
    public String toString() {
        return "Task#" + id + " (for " + username + "): [" + taskName + "] => " + details;
    }

    @DAO.DBField
    @WizardField(displayText = "User who must perform the task")
    public String username;

    @DAO.DBField(columnName = "task_name")
    @WizardField(displayText = "Task name (short)")
    public String taskName;

    @DAO.DBField
    @WizardField(displayText = "Details")
    public String details;
}
