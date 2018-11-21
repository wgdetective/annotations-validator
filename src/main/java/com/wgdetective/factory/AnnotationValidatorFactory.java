package com.wgdetective.factory;

import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.filter.PackageFilter;
import com.wgdetective.tree.AnnotationValidationTree;
import com.wgdetective.validator.AnnotationValidator;
import com.wgdetective.validator.AnnotationValidatorImpl;

import java.lang.annotation.Annotation;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorFactory {
    private AnnotationValidationTreeFactory treeFactory = new AnnotationValidationTreeFactory();

    public <A extends Annotation, T> AnnotationValidator<T> create(final Class<T> clazz,
                                                                final AnnotationProcessor<A> annotationProcessor,
                                                                final PackageFilter packageFilter) {
        final AnnotationValidationTree<T> tree = treeFactory.create(clazz, annotationProcessor, packageFilter);
        return new AnnotationValidatorImpl<>(tree, annotationProcessor);
    }
}
