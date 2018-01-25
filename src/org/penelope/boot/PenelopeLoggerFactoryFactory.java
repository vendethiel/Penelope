package org.penelope.boot;

import org.penelope.di.AutoInject;
import org.penelope.logger.ErrorLogger;
import org.penelope.logger.ILoggerWithNext;
import org.penelope.logger.Logger;
import org.penelope.logger.LoggerFactory;
import org.penelope.ui.UIMain;

/**
 * Uses:
 *  - Abstract Factory Pattern
 */
public class PenelopeLoggerFactoryFactory {
    public PenelopeLoggerFactoryFactory() {
    }

    public ILoggerWithNext make() {
        LoggerFactory<Logger> lf = new LoggerFactory<>(Logger::new);
        lf.add("[debug]");
        lf.add("[info]");
        lf.add(new ErrorLogger().withTask("[error]"));
        return lf.getLast();
    }
}
