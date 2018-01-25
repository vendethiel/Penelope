package org.penelope.model;

import org.penelope.persistence.DAO;

@DAO.Table(tableName = "groups")
public class Group implements DomainObject {
    @DAO.DBField(isPk = true)
    public int id;

    @Override
    public String toString() {
        return "Group#" + id + ": " + name;
    }

    @DAO.DBField
    @WizardField(displayText = "Group name")
    public String name;
}
