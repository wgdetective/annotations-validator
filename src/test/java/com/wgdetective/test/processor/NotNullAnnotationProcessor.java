package com.wgdetective.test.processor;

import com.wgdetective.exception.AnnotationValidateException;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.test.model.NotNull;

/**
 * @author Wladimir Litvinov
 */
public class NotNullAnnotationProcessor implements AnnotationProcessor<NotNull> {
    @Override
    public Class<NotNull> getAnnotation() {
        return NotNull.class;
    }

    @Override
    public boolean filter(final NotNull annotation) {
        return !annotation.comment().equals("ignore");
    }

    @Override
    public void validate(final NotNull a, final Object o) throws AnnotationValidateException {
        if (o == null) {
            throw new AnnotationValidateException();
        }
    }
}
