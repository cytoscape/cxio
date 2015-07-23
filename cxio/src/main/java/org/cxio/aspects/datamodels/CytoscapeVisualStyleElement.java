package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cxio.core.interfaces.AspectElement;

/**
 *
 *
 * @author cmzmasek
 *
 */
public final class CytoscapeVisualStyleElement implements AspectElement {

    private final String                          _title;
    private final List<CytoscapeVisualProperties> _properties;
    public final static String                    NAME     = "visualStyle";
    public final static String                    TITLE    = "title";
    public final static String                    SELECTOR = "selector";
    public final static String                    APPLIES_TO = "applies_to";
    public final static String                    STYLES    = "styles";
    public final static String                    PROPERTIES      = "properties";

    public CytoscapeVisualStyleElement(final String title) {
        _title = title;
        _properties = new ArrayList<CytoscapeVisualProperties>();
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    public final String getTitle() {
        return _title;
    }

    public final List<CytoscapeVisualProperties> getProperties() {
        return _properties;
    }

    public final void addProperties(final CytoscapeVisualProperties properties) {
        _properties.add(properties);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("title: ");
        sb.append(_title);
        sb.append("\n");
        for (final CytoscapeVisualProperties property : _properties) {
            sb.append("selector: ");
            sb.append(property.getSelector());
            sb.append("\n");
            for (final Map.Entry<String, String> entry : property.getProperties().entrySet()) {
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(entry.getValue());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
