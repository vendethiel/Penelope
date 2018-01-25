package org.penelope.logger;

/**
 * Uses:
 *  - "CRTP" Pattern / F-bounded polymorphism
 */
public interface ILoggerWithNext<T extends ILoggerWithNext<T>> extends ILogger {
    T withTask(String task);
    void setNext(ILoggerWithNext next);
}
