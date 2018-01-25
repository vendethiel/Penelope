package org.penelope.ui;

import org.penelope.component.IComponent;
import org.penelope.controller.ControllerFactory;
import org.penelope.controller.IController;
import org.penelope.di.AutoInject;

import java.util.Scanner;

/**
 * Uses:
 *  - Front Controller Pattern
 */
public class UIMain {
    @AutoInject
    private ControllerFactory cf;

    @AutoInject
    private RendererFactory rf;

    private Scanner sc;

    public UIMain() {
        sc = new Scanner(System.in);
    }

    public IController nextAction() {
        System.out.print("> ");
        String line = sc.nextLine();
        String[] parts = line.split(" ", 2);
        String arguments = parts.length > 1 ? parts[1] : "";
        return cf.create(parts[0], arguments);
    }

    public void render(IComponent component) {
        rf.invoke(component);
    }
}
