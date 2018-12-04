package com.wgdetective.tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Wladimir Litvinov
 */
public class FieldInfo {
    private final Field field;
    private final Class<? extends Annotation> annotationClass;
    private final Annotation annotation;

    public FieldInfo(final Field field,
                     final Class<? extends Annotation> annotationClass,
                     final Annotation annotation) {
        this.field = field;
        this.annotationClass = annotationClass;
        this.annotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
