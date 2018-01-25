package org.penelope.logger;

import java.util.function.Supplier;

/**
 * Uses:
 *  - Factory Pattern
 * @param <T> The Logger type
 */
public class LoggerFactory<T extends ILoggerWithNext<T>> {
    Supplier<T> supplier;
    ILoggerWithNext last;

    public LoggerFactory(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T add(String task) {
        T logger = supplier.get().withTask(task);
        logger.setNext(last);
        last = logger;
        return logger;
    }

    public ILoggerWithNext add(ILoggerWithNext logger) {
        logger.setNext(last);
        last = logger;
        return logger;
    }

    public ILoggerWithNext getLast() {
        return last;
    }
}
