package com.wgdetective.validator;

import com.wgdetective.factory.AnnotationValidatorFactory;
import com.wgdetective.test.filter.MyPackageFilter;
import com.wgdetective.test.model.LeafModel;
import com.wgdetective.test.model.LeafModel2;
import com.wgdetective.test.model.RootModel;
import com.wgdetective.test.model.RootModel2;
import com.wgdetective.test.processor.NotNullAnnotationProcessor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorImplTest {
    private AnnotationValidator<RootModel> annotationValidator;
    private AnnotationValidator<RootModel2> root2annotationValidator;

    @Before
    public void init() {
        final AnnotationValidatorFactory factory = new AnnotationValidatorFactory();
        annotationValidator = factory.create(RootModel.class, new NotNullAnnotationProcessor(), new MyPackageFilter());
        root2annotationValidator = factory.create(RootModel2.class, new NotNullAnnotationProcessor(), new MyPackageFilter());
    }

    @Test
    public void simpleTrue() throws Exception {
        final RootModel root = createRoot();

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void simpleFalse() throws Exception {
        final RootModel root = new RootModel();
        root.setName("root");

        assertFalse(annotationValidator.validate(root));
    }

    @Test
    public void leafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void leafFalse() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel();
        root.setLeaf(leaf);

        assertFalse(annotationValidator.validate(root));
    }

    @Test
    public void subLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        final LeafModel subLeaf = new LeafModel(2L);
        leaf.setSubLeaf(subLeaf);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void subLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        final LeafModel subLeaf = new LeafModel();
        leaf.setSubLeaf(subLeaf);

        assertFalse(annotationValidator.validate(root));
    }

    @Test
    public void collectionLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        leafs.add(new LeafModel(1L));
        root.setLeafs(leafs);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void collectionLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        leafs.add(new LeafModel(1L));
        leafs.add(new LeafModel());
        root.setLeafs(leafs);

        assertFalse(annotationValidator.validate(root));
    }

    @Test
    public void circleLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        leaf.setSubLeaf(leaf);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void collectionSubLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        final LeafModel leaf = new LeafModel(1L);
        leaf.setSubLeaf(new LeafModel(2L));
        leafs.add(leaf);
        root.setLeafs(leafs);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void collectionSubLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        final LeafModel leaf = new LeafModel(1L);
        leaf.setSubLeaf(new LeafModel());
        leafs.add(leaf);
        root.setLeafs(leafs);

        assertFalse(annotationValidator.validate(root));
    }

    @Test
    public void collectionSubLeafCircleTrue() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        final LeafModel leaf = new LeafModel(1L);
        final LeafModel subLeaf = new LeafModel(2L);
        subLeaf.setSubLeaf(leaf);
        leaf.setSubLeaf(subLeaf);
        leafs.add(leaf);
        root.setLeafs(leafs);

        assertTrue(annotationValidator.validate(root));
    }

    @Test
    public void cleanLeafsTrueTest() {
        final RootModel2 rootModel2 = new RootModel2();
        rootModel2.setId(1L);
        rootModel2.setLeaf(new LeafModel2(1L));
        assertTrue(root2annotationValidator.validate(rootModel2));
    }

    @Test
    public void cleanLeafsTrueFalse() {
        final RootModel2 rootModel2 = new RootModel2();
        rootModel2.setId(1L);
        rootModel2.setLeaf(new LeafModel2(null));
        assertFalse(root2annotationValidator.validate(rootModel2));
    }

    //list + sub + circle

    private RootModel createRoot() {
        final RootModel root = new RootModel();
        root.setId(1L);
        root.setName("root");
        return root;
    }

}