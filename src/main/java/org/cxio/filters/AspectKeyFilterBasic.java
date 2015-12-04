package org.cxio.filters;

import java.util.HashSet;
import java.util.Set;

public class AspectKeyFilterBasic extends AbstractAspectKeyFilter {

    private Set<String>  _includekeys;
    private Set<String>  _excludekeys;
    private final String _aspect_name;

    public AspectKeyFilterBasic(final String aspect_name) {
        _includekeys = null;
        _excludekeys = null;
        _aspect_name = aspect_name;
    }

    @Override
    public String getAspectName() {
        return _aspect_name;
    }

    @Override
    public Set<String> getIncludeAspectKeys() {
        return _includekeys;
    }

    @Override
    public Set<String> getExcludeAspectKeys() {
        return _excludekeys;
    }

    @Override
    public void addIncludeAspectKey(final String key) {
        if (_includekeys == null) {
            _includekeys = new HashSet<String>();
        }
        _includekeys.add(key);
    }

    @Override
    public void addExcludeAspectKey(final String key) {
        if (_excludekeys == null) {
            _excludekeys = new HashSet<String>();
        }
        _excludekeys.add(key);
    }

}
