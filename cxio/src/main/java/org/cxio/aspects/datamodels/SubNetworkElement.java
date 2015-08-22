package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;

/**
 * This class is used to represent the nodes and edges which make
 * up a sub-network.
 *
 *
 * @author cmzmasek
 *
 */
public final class SubNetworkElement implements AspectElement {

    public final static String NAME         = "subNetworks";

    public final static String SUBNET_NAME  = "name";
    public final static String SUBNET_NODES = "nodes";
    public final static String SUBNET_EDGES = "edges";
    public final static String SUBNET_ID    = "id";

    final String               _name;
    final String               _id;
    final List<String>         _nodes;
    final List<String>         _edges;

    public SubNetworkElement(final String id, final String name) {
        _id = id;
        _name = name;
        _nodes = new ArrayList<String>();
        _edges = new ArrayList<String>();
    }

    final public String getId() {
        return _id;
    }

    final public String getName() {
        return _name;
    }

    final public List<String> getNodes() {
        return _nodes;
    }

    final public List<String> getEdges() {
        return _edges;
    }

    final public void addNode(final String node) {
        _nodes.add(node);
    }

    final public void addEdge(final String edge) {
        _edges.add(edge);
    }

    @Override
    public final String getAspectName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(_id);
        sb.append("\n");
        sb.append("name: ");
        sb.append(_name);
        sb.append("\n");
        sb.append("nodes:");
        for (final String node : _nodes) {
            sb.append(" ");
            sb.append(node);
        }
        sb.append("\n");
        sb.append("edges:");
        for (final String edge : _edges) {
            sb.append(" ");
            sb.append(edge);
        }
        return sb.toString();
    }

}
