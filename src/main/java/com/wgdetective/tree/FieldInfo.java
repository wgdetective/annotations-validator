package com.wgdetective.tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Wladimir Litvinov
 */
public class FieldInfo {
    private final Field field;
    private final Annotation annotation;

    public FieldInfo(final Field field,
                     final Annotation annotation) {
        this.field = field;
        this.annotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
