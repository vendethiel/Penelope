package org.penelope.component;

public class SayComponent implements IComponent {
    private final String message;

    public String getMessage() {
        return message;
    }

    public SayComponent(String message) {
        this.message = message;
    }
}
