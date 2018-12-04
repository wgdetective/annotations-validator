package com.wgdetective.tree;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidationTree<T> {
    private Class<T> clazz;

    private final List<FieldInfo> validatedFields;
    private final Map<Field, AnnotationValidationTree> leafs;

    public AnnotationValidationTree(final Class<T> clazz) {
        this.clazz = clazz;
        this.validatedFields = new ArrayList<>();
        this.leafs = new HashMap<>();

    }

    public void addForValidation(final Field field,
                                 final Class<? extends Annotation> annotationClass,
                                 final Annotation annotation) {
        validatedFields.add(new FieldInfo(field, annotationClass, annotation));
    }

    public void add(final Field field, final AnnotationValidationTree<?> tree) {
        leafs.put(field, tree);
    }

    public void clearUnnecessaryLeafs() {
        for (final Field field : new ArrayList<>(leafs.keySet())) {
            final AnnotationValidationTree leafTree = leafs.get(field);
            if (!leafTree.hasLeafs() && leafTree.getValidatedFields().isEmpty()) {
                leafs.remove(field);
            }
        }
    }

    public boolean hasLeafs() {
        return !leafs.isEmpty();
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public List<FieldInfo> getValidatedFields() {
        return validatedFields;
    }

    public Map<Field, AnnotationValidationTree> getLeafs() {
        return leafs;
    }

}
