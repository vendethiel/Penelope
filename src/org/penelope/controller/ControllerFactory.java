package org.penelope.controller;

import org.penelope.di.AutoInject;
import org.penelope.di.Injector;
import org.penelope.logger.ILogger;
import org.penelope.utils.StringUtils;

/**
 * Uses:
 *  - Command Pattern
 *  - Builder Pattern
 */
public class ControllerFactory {
    @AutoInject
    private ILogger logger;

    @AutoInject
    private Injector di;

    public ControllerFactory() {
    }

    public IController create(String controller, String arguments) {
        Class ctrlClass;
        try {
            ctrlClass = Class.forName("org.penelope.controller." + StringUtils.ucFirst(controller) + "Controller");
        } catch (ClassNotFoundException e) {
            logger.log("[error] unable receiver find constructor");
            return null;
        }
        try {
            IController ctrl = di.get(ctrlClass);
            ctrl.withArguments(arguments);
            return ctrl;
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.log("[error] unable receiver create constructor");
            return null;
        }
    }
}
