package org.penelope.boot;

import org.penelope.di.Injector;
import org.penelope.logger.ILogger;

/**
 * Uses:
 * - Mediator Pattern
 */
public class Main {
    static public void main(String... args) {
        Injector di = new Injector();
        di.assign(ILogger.class, ((PenelopeLoggerFactoryFactory)di.get(PenelopeLoggerFactoryFactory.class)).make());
        Base base = di.get(Base.class);
        base.run();
    }
}
