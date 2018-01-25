package org.penelope.ui;

import org.penelope.component.IComponent;
import org.penelope.component.SayComponent;

public class SayRenderer implements IRenderer<SayComponent> {
    @Override
    public void render(SayComponent component) {
        System.out.println(component.getMessage());
    }
}
