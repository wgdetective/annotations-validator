package com.wgdetective.filter;

/**
 * @author Wladimir Litvinov
 */
public interface PackageFilter {
    boolean filter(final String packageName);
}
