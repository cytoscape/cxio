package org.cxio.aspects.datamodels;

import java.util.SortedMap;
import java.util.TreeMap;

public final class CytoscapeVisualProperties {

    private final SortedMap<String, String> _properties;
    private final String                    _applies_to;
    private final String                    _selector;

    public CytoscapeVisualProperties(final String selector, String applies_to) {
        _selector = selector;
        _applies_to = applies_to;
        _properties = new TreeMap<String, String>();
    }

    public final String getSelector() {
        return _selector;
    }
    
    public final String getAppliesTo() {
        return  _applies_to;
    }

    public final void put(final String name, final String value) {
        _properties.put(name, value);
    }

    public final SortedMap<String, String> getProperties() {
        return _properties;
    }

}
