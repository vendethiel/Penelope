package org.penelope.di;

import org.penelope.meta.FieldFetcher;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private Map<Class, Object> obj;

    public Injector() {
        obj = new HashMap<>();
        obj.put(Injector.class, this);
    }

    public void assign(Class klass, Object o) {
        if (!klass.isInstance(o)) {
            throw new IllegalArgumentException("DI: trying to assign wrong argument type to class: " + klass);
        }
        if (obj.containsKey(klass)) {
            throw new IllegalArgumentException("DI: trying to override already-created class: " + klass);
        }
        obj.put(klass, o);
    }

    public <T> T get(Class klass) {
        if (obj.containsKey(klass)) {
            Object inst = obj.get(klass);
            if (inst == null) {
                throw new RuntimeException("DI: tried to recursively instantiate: " + klass.getName());
            }
            return (T) inst;
        }
        /* mark the class as being constructed to detect recursive instantiations */
        obj.put(klass, null);

        Object inst;
        try {
            inst = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("DI: unable to instantiate a new instance: " + klass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("DI: unable to access ctor: " + klass.getName());
        }
        FieldFetcher<AutoInject> ff = new FieldFetcher<>(klass, AutoInject.class);
        for (Map.Entry<Field, AutoInject> field : ff.get().entrySet()) {
            try {
                /* make the field public _temporarily_ */
                field.getKey().setAccessible(true);
                field.getKey().set(inst, get(field.getKey().getType()));
                field.getKey().setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to set field: " + field);
            }
        }
        /* replace the null with the correctly-constructed object */
        obj.put(klass, inst);
        return (T) inst;
    }
}
