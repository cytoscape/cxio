package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.util.Util;

/**
 * This class is used to represent a group of nodes in network.
 *
 *
 * @author cmzmasek
 *
 */
public final class CyGroupsElement extends AbstractAspectElement {

    public final static String EXTERNAL_EDGES = "external_edges";
    public final static String GROUP_ID       = "group";
    public final static String GROUP_NAME     = "name";
    public final static String INTERNAL_EDGES = "internal_edges";
    public final static String ASPECT_NAME    = "cyGroups";
    public final static String NODES          = "nodes";
    public final static String VIEW           = "view";

    private final List<String> _external_edges;
    private final String       _group_id;
    private final List<String> _internal_edges;
    private final String       _name;
    private final List<String> _nodes;
    private final String       _view;

    public CyGroupsElement(final String group_id, final String view, final String name) {
        _name = name;
        _view = view;
        _group_id = group_id;
        _nodes = new ArrayList<String>();
        _internal_edges = new ArrayList<String>();
        _external_edges = new ArrayList<String>();
    }

    final public void addExternalEdge(final String edge_id) {
        _external_edges.add(edge_id);
    }

    final public void addInternalEdge(final String edge_id) {
        _internal_edges.add(edge_id);
    }

    final public void addNode(final String node_id) {
        _nodes.add(node_id);
    }

    @Override
    public final String getAspectName() {
        return ASPECT_NAME;
    }

    final public List<String> getExternalEdges() {
        return _external_edges;
    }

    final public String getGroupId() {
        return _group_id;
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

    final public String getView() {
        return _view;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ASPECT_NAME);
        sb.append(": ");
        sb.append("name: ");
        sb.append(_name);
        sb.append("\n");
        sb.append("group id: ");
        sb.append(_group_id);
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
