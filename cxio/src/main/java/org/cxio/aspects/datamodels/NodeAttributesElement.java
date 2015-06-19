package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cxio.tools.Util;

/**
 * This class is used to represent a Cytoscape node attribute aspect element.
 *
 *
 * @author cmzmasek
 *
 */
public final class NodeAttributesElement extends AbstractAttributesElement {

    private final List<String> _nodes;
    public final static String NAME  = "nodeAttributes";
    public final static String NODES = "nodes";

    public NodeAttributesElement() {
        _id = null;
        _nodes = new ArrayList<String>();
    }

    public NodeAttributesElement(final String id) {
        _id = id;
        _nodes = new ArrayList<String>();
    }

    public NodeAttributesElement(final String id, final String node_id) {
        _id = id;
        _nodes = new ArrayList<String>();
        addNode(node_id);
    }

    public final void addNode(final long node_id) {
        addNode(String.valueOf(node_id));
    }

    public final void addNode(final String node_id) {
        if (Util.isEmpty(node_id)) {
            throw new IllegalArgumentException("attempt to add null or empty node id");
        }
        _nodes.add(node_id);
    }

    public final void addNodes(final List<String> node_ids) {
        _nodes.addAll(node_ids);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof NodeAttributesElement) && _id.equals(((NodeAttributesElement) o).getId());

    }

    @Override
    public String getAspectName() {
        return NodeAttributesElement.NAME;
    }

    public final List<String> getNodes() {
        return _nodes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(_id);
        sb.append("\n");
        sb.append("nodes: ");
        sb.append(_nodes);
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
