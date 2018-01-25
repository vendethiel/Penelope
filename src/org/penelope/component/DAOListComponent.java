package org.penelope.component;

import org.penelope.model.DomainObject;

import java.util.List;

public class DAOListComponent<Model extends DomainObject> implements IComponent {
    private List<Model> models;

    public DAOListComponent(List<Model> models) {
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

    public boolean isEmpty() {
        return models.isEmpty();
    }
}

