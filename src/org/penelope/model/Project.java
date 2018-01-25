package org.penelope.model;

import org.penelope.persistence.DAO;

@DAO.Table(tableName = "projects")
public class Project implements DomainObject {
    @DAO.DBField(isPk = true)
    public int id;

    @Override
    public String toString() {
        return "Project#" + id + " – '" + name
                + "' (ETA: " + estimatedTime + ") => \"" + details + "\"";
    }

    @DAO.DBField
    @WizardField(displayText = "Project name")
    public String name;

    @DAO.DBField
    @WizardField(displayText = "Details")
    public String details;

    @DAO.DBField(columnName = "estimated_time")
    @WizardField(displayText = "Estimated Time")
    public String estimatedTime;
}
