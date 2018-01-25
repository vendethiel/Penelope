package org.penelope.controller;

import org.penelope.component.DAOListComponent;
import org.penelope.component.IComponent;
import org.penelope.component.SayComponent;
import org.penelope.di.AutoInject;
import org.penelope.di.Injector;
import org.penelope.meta.FieldFetcher;
import org.penelope.model.DomainObject;
import org.penelope.model.WizardField;
import org.penelope.persistence.AppConnection;
import org.penelope.persistence.DAO;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Uses:
 *  - Adapter Pattern
 * @param <Model> The model to generate
 */
public class AutoController<Model extends DomainObject> extends AbstractController {
    private Class modelClass;
    private FieldFetcher<WizardField> ff;
    private DAO<Model> modelDAO;

    @AutoInject
    private AppConnection cnt;

    public AutoController() {
    }

    @Override
    public IComponent perform() {
        Scanner sc = new Scanner(System.in);
        switch (arguments) {
            case "list":
                return new DAOListComponent<Model>(modelDAO.load());
            case "delete":
                System.out.print("Enter id: ");
                if (modelDAO.delete(sc.nextInt())) {
                    return new SayComponent("Deleted!");
                } else {
                    return new SayComponent("Unable to delete this record.");
                }
            case "create":
                try {
                    Model inst = (Model) modelClass.newInstance();
                    for (Map.Entry<Field, WizardField> field : ff.get().entrySet()) {
                        System.out.print("Please enter - " + field.getValue().displayText() + ": ");
                        String value = sc.nextLine();
                        field.getKey().set(inst, value);
                    }
                    modelDAO.save(inst);
                    return new SayComponent("OK!");
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    return new SayComponent("KO :(");
                }
            default:
                return null;
        }
    }

    void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
        ff = new FieldFetcher<>(modelClass, WizardField.class, FieldFetcher.PUBLIC);
        modelDAO = new DAO<>(modelClass);
        modelDAO.setConnection(cnt);
    }
}
