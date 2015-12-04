package org.cxio.filters;

public abstract class AbstractAspectKeyFilter implements AspectKeyFilter {

    @Override
    public boolean isPass(final String key) {
        if ((getIncludeAspectKeys() != null) && !getIncludeAspectKeys().isEmpty()) {
            return getIncludeAspectKeys().contains(key);
        }
        else if ((getExcludeAspectKeys() != null) && !getExcludeAspectKeys().isEmpty()) {
            return !getExcludeAspectKeys().contains(key);
        }
        return true;
    }

}
