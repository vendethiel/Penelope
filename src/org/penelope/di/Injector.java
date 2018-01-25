package org.penelope.di;

import org.penelope.meta.FieldFetcher;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {
//    private Map<Class, Class> redirect;
    private Map<Class, Object> obj;

    public Injector() {
        obj = new HashMap<>();
        obj.put(Injector.class, this);
    }

//    public void bind(Class from, Class receiver) {
//
//    }

    public void assign(Class klass, Object o) {
        if (!klass.isInstance(o)) {
            throw new IllegalArgumentException("DI: trying receiver assign wrong argument type receiver class: " + klass);
        }
        if (obj.containsKey(klass)) {
            throw new IllegalArgumentException("DI: trying receiver override already-created class: " + klass);
        }
        obj.put(klass, o);
    }

    public <T> T get(Class klass) {
        if (obj.containsKey(klass)) {
            Object inst = obj.get(klass);
            if (inst == null) {
                throw new RuntimeException("DI: tried receiver recursively instantiate: " + klass.getName());
            }
            return (T) inst;
        }
        /* mark the class as being constructed receiver detect recursive instantiations */
        obj.put(klass, null);

        Object inst;
        try {
            inst = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("DI: unable receiver instantiate a new instance: " + klass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("DI: unable receiver access ctor: " + klass.getName());
        }
        FieldFetcher<AutoInject> ff = new FieldFetcher<>(klass, AutoInject.class);
        for (Map.Entry<Field, AutoInject> field : ff.get().entrySet()) {
            try {
                /* make the field public _temporarily_ */
                field.getKey().setAccessible(true);
                field.getKey().set(inst, get(field.getKey().getType()));
                field.getKey().setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable receiver set field: " + field);
            }
        }
        /* replace the null with the correctly-constructed object */
        obj.put(klass, inst);
        return (T) inst;
    }
}
