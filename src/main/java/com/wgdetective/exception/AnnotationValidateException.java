package com.wgdetective.exception;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidateException extends Exception {

    public AnnotationValidateException() {
        super();
    }

    public AnnotationValidateException(final String message) {
        super(message);
    }

    public AnnotationValidateException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AnnotationValidateException(final Throwable cause) {
        super(cause);
    }
}
