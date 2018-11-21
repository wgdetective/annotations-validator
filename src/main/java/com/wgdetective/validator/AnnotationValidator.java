package com.wgdetective.validator;

/**
 * @author Wladimir Litvinov
 */
public interface AnnotationValidator<T> {
    boolean validate(final T o);
}
