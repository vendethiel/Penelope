package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.di.AutoInject;
import org.penelope.model.User;

public class UserController extends AbstractController {
    @AutoInject
    private AutoController<User> ac;

    public UserController() {
    }

    public IComponent perform() {
        ac.withArguments(arguments);
        ac.setModelClass(User.class);
        return ac.perform();
        // TODO addToGroup
    }
}
