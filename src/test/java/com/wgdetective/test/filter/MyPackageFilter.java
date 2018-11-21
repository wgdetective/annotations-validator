package com.wgdetective.test.filter;

import com.wgdetective.filter.PackageFilter;

/**
 * @author Wladimir Litvinov
 */
public class MyPackageFilter implements PackageFilter {
    @Override
    public boolean filter(final String packageName) {
        return packageName.startsWith("com.wgdetective");
    }
}
