package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.di.AutoInject;
import org.penelope.model.Message;

public class MessageController extends AbstractController {
    @AutoInject
    private AutoController<Message> ac;

    public MessageController() {
    }

    public IComponent perform() {
        ac.withArguments(arguments);
        ac.setModelClass(Message.class);
        return ac.perform();
    }
}
