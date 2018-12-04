package com.wgdetective.validator;

import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.tree.AnnotationValidationTree;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorImpl<A extends Annotation, T> implements AnnotationValidator<T> {
    private AnnotationValidationTree<T> tree;
    private AnnotationProcessor<A> annotationProcessor;

    public AnnotationValidatorImpl(final AnnotationValidationTree<T> tree,
                                   final AnnotationProcessor<A> annotationProcessor) {
        this.tree = tree;
        this.annotationProcessor = annotationProcessor;
    }

    @Override
    public boolean validate(final T o) {
        return validateLeaf(o, tree, new HashMap<>());
    }

    private boolean validateLeaf(final Object o,
                                 final AnnotationValidationTree<?> tree,
                                 final Map<Object, Boolean> validatedObjects) {
        if (!validatedObjects.containsKey(o)) {
            validatedObjects.put(o, true);
            for (final Map.Entry<Field, Annotation> e : tree.getValidatedFields().entrySet()) {
                if (!annotationProcessor.validate((A) e.getValue(), getField(o, e.getKey()))) {
                    validatedObjects.put(o, false);
                    return false;
                }
            }
            for (final Map.Entry<Field, AnnotationValidationTree> e : tree.getLeafs().entrySet()) {
                final Field field = e.getKey();
                final Object fieldValue = getField(o, field);
                if (fieldValue != null) {
                    if (field.getGenericType() instanceof Class) {
                        if (validateElement(e.getValue(), fieldValue, validatedObjects)) {
                            validatedObjects.put(o, false);
                            return false;
                        }
                    } else if (fieldValue instanceof Collection) {
                        if (validateCollection((Collection) fieldValue, e.getValue(), validatedObjects)) {
                            validatedObjects.put(o, false);
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return validatedObjects.get(o);
    }

    private Object getField(final Object o, final Field field) {
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, o);
    }

    private boolean validateElement(final AnnotationValidationTree tree,
                                    final Object fieldValue,
                                    final Map<Object, Boolean> validatedObjects) {
        return !validateLeaf(fieldValue, tree, validatedObjects);
    }

    private boolean validateCollection(final Collection o, final AnnotationValidationTree tree,
                                       final Map<Object, Boolean> validatedObjects) {
        for (final Object element : o) {
            if (validateElement(tree, element, validatedObjects)) {
                return true;
            }
        }
        return false;
    }
}
