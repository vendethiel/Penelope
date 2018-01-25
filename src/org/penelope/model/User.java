package org.penelope.model;

import org.penelope.persistence.DAO;

@DAO.Table(tableName = "users")
public class User implements DomainObject {
    @DAO.DBField(isPk = true)
    public int id;

    @Override
    public String toString() {
        return "User#" + id + ": " + username;
    }

    @DAO.DBField
    @WizardField(displayText = "User name")
    public String username;
}
