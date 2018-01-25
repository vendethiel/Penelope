package org.penelope.logger;

public class ErrorLogger extends Logger {
    @Override
    protected String format(String message) {
        return message.toUpperCase();
    }
}
