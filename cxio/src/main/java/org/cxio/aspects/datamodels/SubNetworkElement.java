package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent the nodes and edges which make
 * up a sub-network.
 *
 *
 * @author cmzmasek
 *
 */
public final class SubNetworkElement extends AbstractAspectElement {

    public final static String      ASPECT_NAME  = "subNetworks";
    public final static String      SUBNET_EDGES = "edges";
    public final static String      SUBNET_ID    = "id";
    public final static String      SUBNET_NODES = "nodes";

    final private ArrayList<String> _edges;
    private boolean                 _edges_all;
    final private String            _id;
    final private ArrayList<String> _nodes;
    private boolean                 _nodes_all;

    public SubNetworkElement(final String id) {
        _id = id;
        _nodes = new ArrayList<String>();
        _edges = new ArrayList<String>();
        setNodesAll(false);
        setEdgesAll(false);
    }

    final public void addEdge(final String edge) {
        _edges.add(edge);
        setEdgesAll(false);
    }

    final public void addNode(final String node) {
        _nodes.add(node);
        setNodesAll(false);
    }

    @Override
    public final String getAspectName() {
        return ASPECT_NAME;
    }

    final public List<String> getEdges() {
        return _edges;
    }

    final public String getId() {
        return _id;
    }

    final public List<String> getNodes() {
        return _nodes;
    }

    public boolean isEdgesAll() {
        return _edges_all;
    }

    public boolean isNodesAll() {
        return _nodes_all;
    }

    public void setEdgesAll(final boolean edges_all) {
        _edges_all = edges_all;
        if (edges_all) {
            _edges.clear();
        }
    }

    public void setNodesAll(final boolean nodes_all) {
        _nodes_all = nodes_all;
        if (nodes_all) {
            _nodes.clear();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("\n");
        sb.append("id: ");
        sb.append(_id);
        sb.append("\n");

        sb.append("nodes:");
        if (isNodesAll()) {
            sb.append(" all");
        }
        else {
            for (final String node : _nodes) {
                sb.append(" ");
                sb.append(node);
            }
        }
        sb.append("\n");
        sb.append("edges:");
        if (isEdgesAll()) {
            sb.append(" all");
        }
        else {
            for (final String edge : _edges) {
                sb.append(" ");
                sb.append(edge);
            }
        }
        return sb.toString();
    }

}
