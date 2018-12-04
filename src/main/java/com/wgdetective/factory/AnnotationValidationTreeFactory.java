package com.wgdetective.factory;

import com.wgdetective.filter.PackageFilter;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.tree.AnnotationValidationTree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidationTreeFactory {

    public <T> AnnotationValidationTree<T> create(final Class<T> clazz,
                                                  final AnnotationProcessor annotationProcessor,
                                                  final PackageFilter packageFilter) {
        return create(clazz, Collections.singletonList(annotationProcessor), packageFilter, new HashMap<>());
    }

    public <T> AnnotationValidationTree<T> create(final Class<T> clazz,
                                                  final List<AnnotationProcessor> annotationProcessors,
                                                  final PackageFilter packageFilter) {
        return create(clazz, annotationProcessors, packageFilter, new HashMap<>());
    }

    public <A extends Annotation, T> AnnotationValidationTree<T> create(final Class<T> clazz,
                                                                        final List<AnnotationProcessor> annotationProcessors,
                                                                        final PackageFilter packageFilter,
                                                                        final Map<Class<?>, AnnotationValidationTree<?>> proceedClasses) {
        if (!proceedClasses.containsKey(clazz)) {
            final AnnotationValidationTree<T> tree = new AnnotationValidationTree<>(clazz);
            proceedClasses.put(clazz, tree);
            for (final Field field : clazz.getDeclaredFields()) {
                checkAnnotations(annotationProcessors, tree, field);
                final Class<?> fieldClazz = getFieldClass(field);
                processNext(annotationProcessors, packageFilter, tree, field, fieldClazz, proceedClasses);
            }
            tree.clearUnnecessaryLeafs();
        }
        return (AnnotationValidationTree<T>) proceedClasses.get(clazz);
    }

    private <T> void checkAnnotations(final List<AnnotationProcessor> annotationProcessors,
                                      final AnnotationValidationTree<T> tree,
                                      final Field field) {
        for (AnnotationProcessor annotationProcessor : annotationProcessors) {
            final Class annotationClass = annotationProcessor.getAnnotation();
            if (field.isAnnotationPresent(annotationClass)) {
                final Annotation annotation = field.getAnnotation(annotationClass);
                if (annotationProcessor.filter(annotation)) {
                    tree.addForValidation(field, annotationClass, annotation);
                }
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

    private <A extends Annotation, T> void processNext(final List<AnnotationProcessor> annotationProcessors,
                                                       final PackageFilter packageFilter,
                                                       final AnnotationValidationTree<?> tree,
                                                       final Field field,
                                                       final Class<T> fieldClazz,
                                                       final Map<Class<?>, AnnotationValidationTree<?>> proceedClasses) {
        if (fieldClazz != null && packageFilter.filter(fieldClazz.getPackage().getName())) {
            tree.add(field, create(fieldClazz, annotationProcessors, packageFilter, proceedClasses));
        }
    }
}
