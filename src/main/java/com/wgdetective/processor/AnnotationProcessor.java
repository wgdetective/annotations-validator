package com.wgdetective.processor;

import com.wgdetective.exception.AnnotationValidateException;

import java.lang.annotation.Annotation;

/**
 * @author Wladimir Litvinov
 */
public interface AnnotationProcessor<A extends Annotation> {
    Class<A> getAnnotation();
    boolean filter(final A annotation);

    void validate(final A annotation, final Object o) throws AnnotationValidateException;
}
