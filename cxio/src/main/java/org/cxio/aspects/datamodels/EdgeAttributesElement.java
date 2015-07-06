package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cxio.core.CxConstants;
import org.cxio.util.Util;

/**
 * This class is used to represent a Cytoscape edge attribute aspect element.
 *
 *
 * @author cmzmasek
 *
 */
public final class EdgeAttributesElement extends AbstractAttributesElement {

    private final List<String> _edges;
    public final static String NAME  = "edgeAttributes";
    public final static String EDGES = "edges";
    public final static String ID    = CxConstants.ID;

    public EdgeAttributesElement() {
        _id = null;
        _edges = new ArrayList<String>();

    }

    public EdgeAttributesElement(final String id) {
        _id = id;
        _edges = new ArrayList<String>();
    }

    public EdgeAttributesElement(final String id, final String edge_id) {
        _id = id;
        _edges = new ArrayList<String>();
        addEdge(edge_id);
    }

    public final void addEdge(final long edge_id) {
        addEdge(String.valueOf(edge_id));
    }

    public final void addEdge(final String edge_id) {
        if (Util.isEmpty(edge_id)) {
            throw new IllegalArgumentException("attempt to add null or empty edge id");
        }
        _edges.add(edge_id);
    }

    public final void addEdges(final List<String> edge_ids) {
        _edges.addAll(edge_ids);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof EdgeAttributesElement) && _id.equals(((EdgeAttributesElement) o).getId());

    }

    @Override
    public String getAspectName() {
        return EdgeAttributesElement.NAME;
    }

    public final List<String> getEdges() {
        return _edges;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(_id);
        sb.append("\n");
        sb.append("edges: ");
        sb.append(_edges);
        sb.append("\n");
        sb.append("attributes:");
        for (final Map.Entry<String, List<String>> entry : _attributes.entrySet()) {
            sb.append("\n");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            if (_attributes_types.get(entry.getKey()) != null) {
                sb.append(" (");
                sb.append(_attributes_types.get(entry.getKey()));
                sb.append(")");
            }
        }
        return sb.toString();
    }

}
