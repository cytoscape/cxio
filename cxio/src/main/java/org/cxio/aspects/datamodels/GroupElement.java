package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;

public final class GroupElement implements AspectElement {

    public final static String GROUP_NODE     = "group_node";
    public final static String BELONGS_TO     = "belongs_to";
    public final static String EXTERNAL_EDGES = "external_edges";
    public final static String GROUP_NAME     = "name";
    public final static String INTERNAL_EDGES = "internal_edges";
    public final static String NAME           = "Group";
    public final static String NODES          = "nodes";

    private final String       _belongs_to;
    private final List<String> _external_edges;
    private final String       _group_node;
    private final List<String> _internal_edges;
    private final String       _name;
    private final List<String> _nodes;

    public GroupElement(final String group_node, final String name) {
        _name = name;
        _belongs_to = null;
        _group_node = group_node;
        _nodes = new ArrayList<String>();
        _internal_edges = new ArrayList<String>();
        _external_edges = new ArrayList<String>();
    }

    public GroupElement(final String group_node, final String name, final String belongs_to) {
        _name = name;
        _belongs_to = belongs_to;
        _group_node = group_node;
        _nodes = new ArrayList<String>();
        _internal_edges = new ArrayList<String>();
        _external_edges = new ArrayList<String>();
    }

    final public void addNode(final String node) {
        _nodes.add(node);
    }

    @Override
    public final String getAspectName() {
        return NAME;
    }

    final public String getBelongsTo() {
        return _belongs_to;
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

}
