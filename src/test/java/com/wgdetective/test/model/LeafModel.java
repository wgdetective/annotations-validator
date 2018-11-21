package com.wgdetective.test.model;

/**
 * @author Wladimir Litvinov
 */
public class LeafModel {
    @NotNull
    private Long id;
    @NotNull(comment = "ignore")
    private LeafModel subLeaf;
    private SimpleModel simpleModel;

    public LeafModel() {
    }

    public LeafModel(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LeafModel getSubLeaf() {
        return subLeaf;
    }

    public void setSubLeaf(final LeafModel subLeaf) {
        this.subLeaf = subLeaf;
    }

    public SimpleModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(final SimpleModel simpleModel) {
        this.simpleModel = simpleModel;
    }
}
