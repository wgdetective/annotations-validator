package com.wgdetective.factory;

import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.filter.PackageFilter;
import com.wgdetective.tree.AnnotationValidationTree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidationTreeFactory {

    public <A extends Annotation, T> AnnotationValidationTree<T> create(final Class<T> clazz,
                                                                     final AnnotationProcessor<A> annotationProcessor,
                                                                     final PackageFilter packageFilter) {
        return create(clazz, annotationProcessor, packageFilter, new HashMap<>());
    }

    public <A extends Annotation, T> AnnotationValidationTree<T> create(final Class<T> clazz,
                                                                     final AnnotationProcessor<A> annotationProcessor,
                                                                     final PackageFilter packageFilter,
                                                                     final Map<Class<?>, AnnotationValidationTree<?>> proceedClasses) {
        if (!proceedClasses.containsKey(clazz)) {
            final AnnotationValidationTree<T> tree = new AnnotationValidationTree<>(clazz);
            proceedClasses.put(clazz, tree);
            for (final Field field : clazz.getDeclaredFields()) {
                checkAnnotation(annotationProcessor, tree, field);
                final Class<?> fieldClazz = getFieldClass(field);
                processNext(annotationProcessor, packageFilter, tree, field, fieldClazz, proceedClasses);
            }
            tree.clearUnnecessaryLeafs();
        }
        return (AnnotationValidationTree<T>) proceedClasses.get(clazz);
    }

    private <A extends Annotation, T> void checkAnnotation(final AnnotationProcessor<A> annotationProcessor,
                                                           final AnnotationValidationTree<T> tree,
                                                           final Field field) {
        if (field.isAnnotationPresent(annotationProcessor.getAnnotation())) {
            final A annotation = field.getAnnotation(annotationProcessor.getAnnotation());
            if (annotationProcessor.filter(annotation)) {
                tree.addForValidation(field, annotation);
            }
        }
    }

    private Class<?> getFieldClass(final Field field) {
        Class<?> fieldClazz = null;
        if (field.getGenericType() instanceof Class) {
            fieldClazz = (Class<?>) field.getGenericType();
        } else if (field.getGenericType() instanceof ParameterizedType) {
            final Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            if (type instanceof Class) {
                fieldClazz = (Class<?>) type;
            }
        }
        return fieldClazz;
    }

    private <A extends Annotation, T> void processNext(final AnnotationProcessor<A> annotationProcessor,
                                                       final PackageFilter packageFilter,
                                                       final AnnotationValidationTree<?> tree,
                                                       final Field field,
                                                       final Class<T> fieldClazz,
                                                       final Map<Class<?>, AnnotationValidationTree<?>> proceedClasses) {
        if (fieldClazz != null && packageFilter.filter(fieldClazz.getPackage().getName())) {
            tree.add(field, create(fieldClazz, annotationProcessor, packageFilter, proceedClasses));
        }
    }
}
