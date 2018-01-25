package org.penelope.ui;

import org.penelope.component.IComponent;

/**
 * Uses:
 *  - Dynamic Visitor Pattern
 */
public class RendererFactory {
    public RendererFactory() {
    }

    void invoke(IComponent component) {
        String[] ns = getClass().getName().split("\\.");
        ns[ns.length - 1] = component.getClass().getSimpleName().replace("Component", "Renderer");
        String className = String.join(".", ns);

        Class rendererClass;
        try {
            rendererClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            IRenderer renderer = (IRenderer) rendererClass.newInstance();
            renderer.render(component);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
