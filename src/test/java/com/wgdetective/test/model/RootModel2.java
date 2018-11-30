package com.wgdetective.test.model;

/**
 * @author Wladimir Litvinov
 */
public class RootModel2 {
    @NotNull
    private Long id;
    private LeafModel2 leaf;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LeafModel2 getLeaf() {
        return leaf;
    }

    public void setLeaf(final LeafModel2 leaf) {
        this.leaf = leaf;
    }
}
