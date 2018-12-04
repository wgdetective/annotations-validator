package com.wgdetective.validator;

import com.wgdetective.exception.AnnotationValidateException;
import com.wgdetective.factory.AnnotationValidatorFactory;
import com.wgdetective.processor.AnnotationProcessor;
import com.wgdetective.test.filter.MyPackageFilter;
import com.wgdetective.test.model.LeafModel;
import com.wgdetective.test.model.LeafModel2;
import com.wgdetective.test.model.RootModel;
import com.wgdetective.test.model.RootModel2;
import com.wgdetective.test.processor.CheckPrefixAnnotationProcessor;
import com.wgdetective.test.processor.NotNullAnnotationProcessor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wladimir Litvinov
 */
public class AnnotationValidatorImplTest {
    private AnnotationValidator<RootModel> annotationValidator;
    private AnnotationValidator<RootModel2> root2annotationValidator;
    private AnnotationValidator<RootModel> prefixAnnotationValidator;

    private AnnotationValidator<RootModel> multiAnnotationValidator;

    @Before
    public void init() {
        final AnnotationValidatorFactory factory = new AnnotationValidatorFactory();
        final MyPackageFilter packageFilter = new MyPackageFilter();
        final NotNullAnnotationProcessor notNullAnnotationProcessor = new NotNullAnnotationProcessor();
        final CheckPrefixAnnotationProcessor prefixAnnotationProcessor = new CheckPrefixAnnotationProcessor();

        annotationValidator = factory.create(RootModel.class, notNullAnnotationProcessor, packageFilter);
        root2annotationValidator = factory.create(RootModel2.class, notNullAnnotationProcessor, packageFilter);
        prefixAnnotationValidator = factory.create(RootModel.class, prefixAnnotationProcessor, packageFilter);

        final List<AnnotationProcessor> processors = new ArrayList<>();
        processors.add(notNullAnnotationProcessor);
        processors.add(prefixAnnotationProcessor);
        multiAnnotationValidator = factory.create(RootModel.class, processors, packageFilter);
    }

    @Test
    public void simpleTrue() throws Exception {
        final RootModel root = createRoot();

        annotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void simpleFalse() throws Exception {
        final RootModel root = new RootModel();
        root.setName("root");

        annotationValidator.validate(root);
    }

    @Test
    public void leafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);

        annotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void leafFalse() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel();
        root.setLeaf(leaf);

        annotationValidator.validate(root);
    }

    @Test
    public void subLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        final LeafModel subLeaf = new LeafModel(2L);
        leaf.setSubLeaf(subLeaf);

        annotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void subLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        final LeafModel subLeaf = new LeafModel();
        leaf.setSubLeaf(subLeaf);

        annotationValidator.validate(root);
    }

    @Test
    public void collectionLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        leafs.add(new LeafModel(1L));
        root.setLeafs(leafs);

        annotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void collectionLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        leafs.add(new LeafModel(1L));
        leafs.add(new LeafModel());
        root.setLeafs(leafs);

        annotationValidator.validate(root);
    }

    @Test
    public void circleLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final LeafModel leaf = new LeafModel(1L);
        root.setLeaf(leaf);
        leaf.setSubLeaf(leaf);

        annotationValidator.validate(root);
    }

    @Test
    public void collectionSubLeafTrue() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        final LeafModel leaf = new LeafModel(1L);
        leaf.setSubLeaf(new LeafModel(2L));
        leafs.add(leaf);
        root.setLeafs(leafs);

        annotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void collectionSubLeafFalse() throws Exception {
        final RootModel root = createRoot();
        final List<LeafModel> leafs = new ArrayList<>();
        final LeafModel leaf = new LeafModel(1L);
        leaf.setSubLeaf(new LeafModel());
        leafs.add(leaf);
        root.setLeafs(leafs);

        annotationValidator.validate(root);
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

        annotationValidator.validate(root);
    }

    @Test
    public void cleanLeafsTrueTest() throws Exception {
        final RootModel2 rootModel2 = new RootModel2();
        rootModel2.setId(1L);
        rootModel2.setLeaf(new LeafModel2(1L));
        root2annotationValidator.validate(rootModel2);
    }

    @Test(expected = AnnotationValidateException.class)
    public void cleanLeafsFalseTest() throws Exception {
        final RootModel2 rootModel2 = new RootModel2();
        rootModel2.setId(1L);
        rootModel2.setLeaf(new LeafModel2(null));
        root2annotationValidator.validate(rootModel2);
    }

    @Test
    public void checkPrefixTrueTest() throws Exception {
        final RootModel root = createRoot();
        root.setName("pref_" + root.getName());
        prefixAnnotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void checkPrefixFalseTest() throws Exception {
        final RootModel root = createRoot();
        prefixAnnotationValidator.validate(root);
    }

    @Test
    public void multiTrueTest() throws Exception {
        final RootModel root = createRoot();
        root.setName("pref_" + root.getName());
        multiAnnotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void multiFalse1Test() throws Exception {
        final RootModel root = createRoot();
        multiAnnotationValidator.validate(root);
    }

    @Test(expected = AnnotationValidateException.class)
    public void multiFalse2Test() throws Exception {
        final RootModel root = createRoot();
        root.setId(null);
        multiAnnotationValidator.validate(root);
    }

    private RootModel createRoot() {
        final RootModel root = new RootModel();
        root.setId(1L);
        root.setName("root");
        return root;
    }

}