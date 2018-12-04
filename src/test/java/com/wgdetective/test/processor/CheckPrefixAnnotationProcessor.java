package com.wgdetective.test.processor;

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
    public boolean validate(final CheckPrefix annotation, final Object o) {
        return ((String) o).startsWith(annotation.prefix());
    }
}
