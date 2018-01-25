package org.penelope.ui;

import org.penelope.component.DAOListComponent;
import org.penelope.component.SayComponent;

public class DAOListRenderer implements IRenderer<DAOListComponent> {
    @Override
    public void render(DAOListComponent component) {
        if (component.isEmpty()) {
            System.out.println("(No models)");
        } else {
            for (Object model : component.getModels()) {
                System.out.println(" - " + model);
            }
        }
    }
}

