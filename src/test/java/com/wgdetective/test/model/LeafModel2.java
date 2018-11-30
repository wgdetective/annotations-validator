package com.wgdetective.test.model;

/**
 * @author Wladimir Litvinov
 */
public class LeafModel2 {
    @NotNull
    private Long id;

    public LeafModel2(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
