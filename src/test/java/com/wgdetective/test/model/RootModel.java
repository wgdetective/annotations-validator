package com.wgdetective.test.model;


import java.util.List;

/**
 * @author Wladimir Litvinov
 */
public class RootModel {
    @NotNull
    private Long id;
    @CheckPrefix(prefix = "pref_")
    private String name;
    private LeafModel leaf;
    private List<LeafModel> leafs;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LeafModel getLeaf() {
        return leaf;
    }

    public void setLeaf(final LeafModel leaf) {
        this.leaf = leaf;
    }

    public List<LeafModel> getLeafs() {
        return leafs;
    }

    public void setLeafs(final List<LeafModel> leafs) {
        this.leafs = leafs;
    }
}
