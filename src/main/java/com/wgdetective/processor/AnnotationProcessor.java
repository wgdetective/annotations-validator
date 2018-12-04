package com.wgdetective.processor;

import java.lang.annotation.Annotation;

/**
 * @author Wladimir Litvinov
 */
public interface AnnotationProcessor<A extends Annotation> {
    Class<A> getAnnotation();
    boolean filter(final A annotation);
    boolean validate(final A annotation, final Object o);
}
