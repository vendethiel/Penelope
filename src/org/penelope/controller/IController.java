package org.penelope.controller;

import org.penelope.component.IComponent;

public interface IController {
    public IController withArguments(String arguments);
    public IComponent perform();
}
