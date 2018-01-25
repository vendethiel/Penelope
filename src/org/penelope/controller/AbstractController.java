package org.penelope.controller;

public abstract class AbstractController implements IController {
    public String arguments;

    @Override
    public IController withArguments(String arguments) {
        this.arguments = arguments;
        return this;
    }
}
