package com.wgdetective.test.model;

/**
 * @author Wladimir Litvinov
 */
public class SimpleModel {
    @NotNull
    private Long id;
    private String str;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(final String str) {
        this.str = str;
    }
}
