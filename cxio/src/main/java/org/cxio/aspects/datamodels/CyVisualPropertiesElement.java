package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class is used to represent a visual property of a
 * network, node(s), or edge(s) in/under a network view.
 *
 * @author cmzmasek
 *
 */
public final class CyVisualPropertiesElement extends AbstractAspectElement {

    public final static String              APPLIES_TO    = "applies_to";
    public final static String              ASPECT_NAME   = "visualProperties";
    public final static String              VIEW          = "view";
    public final static String              PROPERTIES    = "properties";
    public final static String              PROPERTIES_OF = "properties_of";

    private final List<String>              _applies_to;
    final Long                              _view;
    private final SortedMap<String, String> _properties;
    private final String                    _properties_of;

    public CyVisualPropertiesElement(final String properties_of) {
        _properties_of = properties_of;
        _applies_to = new ArrayList<String>();
        _properties = new TreeMap<String, String>();
        _view = null;
    }

    public CyVisualPropertiesElement(final String properties_of, final long view) {
        _properties_of = properties_of;
        _applies_to = new ArrayList<String>();
        _properties = new TreeMap<String, String>();
        _view = view;
    }

    public CyVisualPropertiesElement(final String properties_of, final List<String> applies_to) {
        _properties_of = properties_of;
        _applies_to = applies_to;
        _properties = new TreeMap<String, String>();
        _view = null;
    }

    public CyVisualPropertiesElement(final String properties_of, final List<String> applies_to, final long view) {
        _properties_of = properties_of;
        _applies_to = applies_to;
        _properties = new TreeMap<String, String>();
        _view = view;
    }

    public final void addAppliesTo(final String applies_to) {
        _applies_to.add(applies_to);
    }

    public final List<String> getAppliesTo() {
        return _applies_to;
    }

    @Override
    public String getAspectName() {
        return ASPECT_NAME;
    }

    final public Long getView() {
        return _view;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("properties of: ");
        sb.append(_properties_of);
        sb.append("\n");
        if (_view != null) {
            sb.append("view: ");
            sb.append(_view);
            sb.append("\n");
        }
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
