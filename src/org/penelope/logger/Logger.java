package org.penelope.logger;

/**
 * Uses:
 *  - Chain of Responsibility Pattern
 */
public class Logger implements ILoggerWithNext<Logger> {
    private String task;
    private ILoggerWithNext next;

    public Logger() {
    }
    public Logger(String task) {
        this.task = task;
    }

    public void log(String message) {
        if (handles(message) || next == null) {
            System.out.println(format(message));
        } else {
            next.log(message);
        }
    }

    protected String format(String message) {
        return message;
    }

    private boolean handles(String message) {
        return task != null && message.startsWith(task);
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Logger withTask(String task) {
        setTask(task);
        return this;
    }

    public void setNext(ILoggerWithNext next) {
        this.next = next;
    }

    public Logger withNext(ILoggerWithNext next) {
        setNext(next);
        return this;
    }
}
