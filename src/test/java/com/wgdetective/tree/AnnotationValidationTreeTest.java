package com.wgdetective.tree;

import com.wgdetective.test.model.RootModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidationTreeTest {
    @Test
    public void createTest() {
        final AnnotationValidationTree<RootModel> tree = new AnnotationValidationTree<>(RootModel.class);
        assertEquals(RootModel.class, tree.getClazz());
    }
}