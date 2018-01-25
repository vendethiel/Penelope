package org.penelope.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppConnection {
    private Connection cnt;

    public AppConnection() {
        // XXX implement settings module
        String dbUrl = "jdbc:postgresql:penelope?user=penelope&password=pp";
        connect(dbUrl);
    }

    public Connection get() {
        return cnt;
    }

    private void connect(String dbUrl) {
        try {
            cnt = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                wait(10000);
            } catch (InterruptedException e1) {
            }
            connect(dbUrl);
        }
    }
}
