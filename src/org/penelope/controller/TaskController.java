package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.di.AutoInject;
import org.penelope.model.Task;

public class TaskController extends AbstractController {
    @AutoInject
    private AutoController<Task> ac;

    public TaskController() {
    }

    public IComponent perform() {
        ac.withArguments(arguments);
        ac.setModelClass(Task.class);
        return ac.perform();
        // TODO addToGroup
    }
}
