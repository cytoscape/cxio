package org.cxio.examples.custom_aspects;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.AbstractAspectElement;

public final class ContextElement extends AbstractAspectElement {

    public final static String              NAME = "context";

    private final SortedMap<String, String> _key_values;

    @Override
    public String getAspectName() {
        return NAME;
    }

    public ContextElement() {
        _key_values = new TreeMap<String, String>();
    }

    public String getValue(final String key) {
        return _key_values.get(key);
    }

    public void put(final String key, final String value) {
        _key_values.put(key, value);
    }

    public final SortedMap<String, String> getContextKeyValues() {
        return _key_values;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, String> e : _key_values.entrySet()) {
            sb.append(e.getKey());
            sb.append(": ");
            sb.append(e.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

}
