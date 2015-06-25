package org.cxio.filters;

import java.util.Set;

public interface AspectKeyFilter {

    public String getAspectName();

    public void addIncludeAspectKey(final String keys);

    public void addExcludeAspectKey(final String keys);

    public Set<String> getIncludeAspectKeys();

    public Set<String> getExcludeAspectKeys();

    public boolean isPass(final String key);

}
