package org.penelope.model;

import org.penelope.persistence.DAO;

@DAO.Table(tableName = "messages")
public class Message implements DomainObject {
    @DAO.DBField(isPk = true)
    public int id;

    @Override
    public String toString() {
        return "Message#" + id + ": from " + sender + " receiver " + receiver + ": " + body;
    }

    @DAO.DBField
    @WizardField(displayText = "Sender")
    public String sender;

    @DAO.DBField
    @WizardField(displayText = "For")
    public String receiver;

    @DAO.DBField
    @WizardField(displayText = "Message body")
    public String body;
}
