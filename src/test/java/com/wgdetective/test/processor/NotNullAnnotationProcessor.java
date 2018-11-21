package com.wgdetective.test.processor;

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
    public boolean validate(final Object o) {
        return o != null;
    }
}
