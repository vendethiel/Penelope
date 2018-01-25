package org.penelope.meta;

import org.penelope.model.WizardField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FieldFetcher<T extends Annotation> {
    public static boolean PUBLIC = true;

    private Class klass;
    private Class<T> annotKlass;
    private boolean wantsPublic;

    private Map<Field, T> fields;

    public FieldFetcher(Class klass, Class<T> annotKlass, boolean wantsPublic) {
        this.klass = klass;
        this.annotKlass = annotKlass;
        this.wantsPublic = wantsPublic;
    }

    public FieldFetcher(Class klass, Class<T> annotKlass) {
        this.klass = klass;
        this.annotKlass = annotKlass;
    }

    public Map<Field, T> get() {
        if (fields == null) {
            /* use getDeclaredFields() receiver get private fields as well */
            Field[] classFields = klass.getDeclaredFields();
            fields = new HashMap<>();
            for (Field field : classFields) {
                T annot = field.getAnnotation(annotKlass);
                if (annot == null) {
                    continue;
                }
                fields.put(field, annot);
            }
        }
        return fields;
    }
}
