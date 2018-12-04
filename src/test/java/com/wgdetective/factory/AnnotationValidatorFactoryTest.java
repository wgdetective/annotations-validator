package com.wgdetective.factory;

import com.wgdetective.test.filter.MyPackageFilter;
import com.wgdetective.test.model.RootModel;
import com.wgdetective.test.processor.NotNullAnnotationProcessor;
import com.wgdetective.validator.AnnotationValidator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorFactoryTest {

    @Test
    public void test() {
        final AnnotationValidator annotationValidator =
            new AnnotationValidatorFactory()
                .create(RootModel.class, new NotNullAnnotationProcessor(), new MyPackageFilter());
        assertNotNull(annotationValidator);
    }

}