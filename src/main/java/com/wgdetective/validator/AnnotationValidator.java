package com.wgdetective.validator;

import com.wgdetective.exception.AnnotationValidateException;

/**
 * @author Wladimir Litvinov
 */
public interface AnnotationValidator<T> {
    void validate(final T o) throws AnnotationValidateException;
}
