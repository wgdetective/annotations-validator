package com.wgdetective.factory;

import com.wgdetective.filter.PackageFilter;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.tree.AnnotationValidationTree;
import com.wgdetective.validator.AnnotationValidator;
import com.wgdetective.validator.AnnotationValidatorImpl;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorFactory {
    private AnnotationValidationTreeFactory treeFactory = new AnnotationValidationTreeFactory();

    public <T> AnnotationValidator<T> create(final Class<T> clazz,
                                             final AnnotationProcessor annotationProcessor,
                                             final PackageFilter packageFilter) {
        return create(clazz, Collections.singletonList(annotationProcessor), packageFilter);
    }

    public <T> AnnotationValidator<T> create(final Class<T> clazz,
                                             final List<AnnotationProcessor> annotationProcessors,
                                             final PackageFilter packageFilter) {
        final AnnotationValidationTree<T> tree = treeFactory.create(clazz, annotationProcessors, packageFilter);
        final Map<Class<Annotation>, AnnotationProcessor> map = annotationProcessors.stream()
            .collect(Collectors.toMap(AnnotationProcessor::getAnnotation, e -> e));
        return new AnnotationValidatorImpl<>(tree, map);
    }
}
