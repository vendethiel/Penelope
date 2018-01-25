package org.penelope.ui;

import org.penelope.component.IComponent;

/**
 * Uses:
 *  - Command Pattern
 * @param <ComponentType> Type of the command
 */
public interface IRenderer<ComponentType extends IComponent> {
    void render(ComponentType component);
}
