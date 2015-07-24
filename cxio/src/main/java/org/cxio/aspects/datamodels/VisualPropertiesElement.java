package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.core.interfaces.AspectElement;

/**
 *
 *
 * @author cmzmasek
 *
 */
public final class VisualPropertiesElement implements AspectElement {

    public final static String              NAME       = "visualProperties";
    public final static String              PROPERTIES_OF       = "properties_of";
    public final static String              APPLIES_TO = "applies_to";
    public final static String              PROPERTIES = "properties";

    private final SortedMap<String, String> _properties;
    private final List<String>              _applies_to;
    private final String                    _properties_of;

    public VisualPropertiesElement(final String properties_of) {
        _properties_of = properties_of;
        _applies_to = new ArrayList<String>();
        _properties = new TreeMap<String, String>();
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    public final List<String> getAppliesTo() {
        return _applies_to;
    }

    public final SortedMap<String, String> getProperties() {
        return _properties;
    }

    public final String getPropertiesOf() {
        return _properties_of;
    }

    public final void putProperty(final String name, final String value) {
        _properties.put(name, value);
    }

    public final void addAppliesTo(final String applies_to) {
        _applies_to.add(applies_to);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("properties_of: ");
        sb.append(_properties_of);
        sb.append("\n");
        sb.append("applies to: ");
        for (final String a : _applies_to) {
            sb.append(a);
            sb.append(" ");
        }
        sb.append("\n");
        for (final Map.Entry<String, String> entry : _properties.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

}
