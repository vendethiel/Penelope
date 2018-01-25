package org.penelope.controller;

import org.penelope.component.IComponent;
import org.penelope.di.AutoInject;
import org.penelope.model.Project;

public class ProjectController extends AbstractController {
    @AutoInject
    private AutoController<Project> ac;

    public ProjectController() {
    }

    public IComponent perform() {
        ac.withArguments(arguments);
        ac.setModelClass(Project.class);
        return ac.perform();
    }
}
