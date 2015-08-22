package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

/**
 * This class is used to represent a group of nodes in network.
 *
 *
 * @author cmzmasek
 *
 */
public final class GroupElement implements AspectElement {

    public final static String GROUP_NODE     = "group_node";
    public final static String VIEW           = "view";
    public final static String EXTERNAL_EDGES = "external_edges";
    public final static String GROUP_NAME     = "name";
    public final static String INTERNAL_EDGES = "internal_edges";
    public final static String NAME           = "groups";
    public final static String NODES          = "nodes";

    private final String       _view;
    private final List<String> _external_edges;
    private final String       _group_node;
    private final List<String> _internal_edges;
    private final String       _name;
    private final List<String> _nodes;

    public GroupElement(final String group_node, final String name) {
        _name = name;
        _view = null;
        _group_node = group_node;
        _nodes = new ArrayList<String>();
        _internal_edges = new ArrayList<String>();
        _external_edges = new ArrayList<String>();
    }

    public GroupElement(final String group_node, final String name, final String view) {
        _name = name;
        _view = view;
        _group_node = group_node;
        _nodes = new ArrayList<String>();
        _internal_edges = new ArrayList<String>();
        _external_edges = new ArrayList<String>();
    }

    final public void addNode(final String node) {
        _nodes.add(node);
    }

    final public void addExternalEdge(final String edge) {
        _external_edges.add(edge);
    }

    final public void addInternalEdge(final String edge) {
        _internal_edges.add(edge);
    }

    @Override
    public final String getAspectName() {
        return NAME;
    }

    final public String getView() {
        return _view;
    }

    final public List<String> getExternalEdges() {
        return _external_edges;
    }

    final public String getGroupNode() {
        return _group_node;
    }

    final public List<String> getInternalEdges() {
        return _internal_edges;
    }

    final public String getName() {
        return _name;
    }

    final public List<String> getNodes() {
        return _nodes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name: ");
        sb.append(_name);
        sb.append("\n");
        sb.append("group node: ");
        sb.append(_group_node);
        sb.append("\n");
        if (!Util.isEmpty(_view)) {
            sb.append("view: ");
            sb.append(_view);
            sb.append("\n");
        }
        sb.append("nodes:");
        for (final String s : _nodes) {
            sb.append(" ");
            sb.append(s);

        }
        sb.append("\n");
        sb.append("internal edges:");
        for (final String s : _internal_edges) {
            sb.append(" ");
            sb.append(s);

        }
        sb.append("\n");
        sb.append("external edges:");
        for (final String s : _external_edges) {
            sb.append(" ");
            sb.append(s);

        }
        sb.append("\n");
        return sb.toString();
    }

}
