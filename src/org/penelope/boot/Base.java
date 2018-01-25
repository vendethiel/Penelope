package org.penelope.boot;

import org.penelope.component.IComponent;
import org.penelope.controller.IController;
import org.penelope.controller.QuitController;
import org.penelope.di.AutoInject;
import org.penelope.logger.ILogger;
import org.penelope.ui.UIMain;

public class Base {
    @AutoInject
    private UIMain ui;

    @AutoInject
    private ILogger logger;

    public Base() {
    }

    public void run() {
        while (true) {
            IController ctrl = ui.nextAction();
            if (ctrl instanceof QuitController) {
                break;
            }
            if (ctrl == null) {
                continue;
            }
            IComponent component = ctrl.perform();
            if (component == null) {
                logger.log("[error] controller failure");
                continue;
            }
            ui.render(component);
        }

    }
}
