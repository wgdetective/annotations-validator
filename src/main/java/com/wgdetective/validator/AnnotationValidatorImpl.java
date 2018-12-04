package com.wgdetective.validator;

import com.wgdetective.exception.AnnotationValidateException;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.tree.AnnotationValidationTree;
import com.wgdetective.tree.FieldInfo;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorImpl<T> implements AnnotationValidator<T> {

    private AnnotationValidationTree<T> tree;
    private Map<Class<Annotation>, AnnotationProcessor> annotationProcessors;

    public AnnotationValidatorImpl(final AnnotationValidationTree<T> tree,
                                   final Map<Class<Annotation>, AnnotationProcessor> annotationProcessors) {
        this.tree = tree;
        this.annotationProcessors = annotationProcessors;
    }

    @Override
    public void validate(final T o) throws AnnotationValidateException {
        validateLeaf(o, tree, new HashMap<>());
    }

    private void validateLeaf(final Object o,
                              final AnnotationValidationTree<?> tree,
                              final Map<Object, Boolean> validatedObjects) throws AnnotationValidateException {
        if (!validatedObjects.containsKey(o)) {
            validatedObjects.put(o, true);
            for (final FieldInfo fieldInfo : tree.getValidatedFields()) {
                final AnnotationProcessor processor =
                    annotationProcessors.get(fieldInfo.getAnnotation().annotationType());
                processor.validate(fieldInfo.getAnnotation(), getField(o, fieldInfo.getField()));
            }
            for (final Map.Entry<Field, AnnotationValidationTree> e : tree.getLeafs().entrySet()) {
                final Field field = e.getKey();
                final Object fieldValue = getField(o, field);
                if (fieldValue != null) {
                    if (field.getGenericType() instanceof Class) {
                        validateElement(e.getValue(), fieldValue, validatedObjects);
                    } else if (fieldValue instanceof Collection) {
                        validateCollection((Collection) fieldValue, e.getValue(), validatedObjects);
                    }
                }
            }
        }
    }

    private Object getField(final Object o, final Field field) {
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, o);
    }

    private void validateElement(final AnnotationValidationTree tree,
                                 final Object fieldValue,
                                 final Map<Object, Boolean> validatedObjects) throws AnnotationValidateException {
        validateLeaf(fieldValue, tree, validatedObjects);
    }

    private void validateCollection(final Collection o, final AnnotationValidationTree tree,
                                    final Map<Object, Boolean> validatedObjects) throws AnnotationValidateException {
        for (final Object element : o) {
            validateElement(tree, element, validatedObjects);
        }
    }
}
