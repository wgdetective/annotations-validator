package com.wgdetective.test.processor;

import com.wgdetective.exception.AnnotationValidateException;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.test.model.CheckPrefix;

/**
 * @author Wladimir Litvinov
 */
public class CheckPrefixAnnotationProcessor implements AnnotationProcessor<CheckPrefix> {
    @Override
    public Class<CheckPrefix> getAnnotation() {
        return CheckPrefix.class;
    }

    @Override
    public boolean filter(final CheckPrefix annotation) {
        return true;
    }

    @Override
    public void validate(final CheckPrefix annotation, final Object o) throws AnnotationValidateException {
        if (!((String) o).startsWith(annotation.prefix())) {
            throw new AnnotationValidateException("Expected prefix was " + annotation.prefix());
        }
    }
}
