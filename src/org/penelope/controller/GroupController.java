package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.di.AutoInject;
import org.penelope.model.Group;

public class GroupController extends AbstractController {
    @AutoInject
    private AutoController<Group> ac;

    public GroupController() {
    }

    public IComponent perform() {
        ac.withArguments(arguments);
        ac.setModelClass(Group.class);
        return ac.perform();
        // TODO addToGroup
    }
}
