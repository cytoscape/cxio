package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

import org.cxio.core.interfaces.AspectElement;

public final class GroupElement implements AspectElement {

    public final static String GROUP_ID      = "id";
    public final static String GROUP_NAME    = "name";
    public final static String GROUP_NETWORK = "network";
    public final static String GROUP_NODES   = "nodes";
    public final static String NAME          = "Group";

    private final String               _id;
    private final String               _name;
    private final String               _network;
    private final List<String>         _nodes;

    public GroupElement(final String id, final String name) {
        _id = id;
        _name = name;
        _network = null;
        _nodes = new ArrayList<String>();
    }

    public GroupElement(final String id, final String name, final String network) {
        _id = id;
        _name = name;
        _network = network;
        _nodes = new ArrayList<String>();
    }

    final public void addNode(final String node) {
        _nodes.add(node);
    }

    @Override
    public final String getAspectName() {
        return NAME;
    }

    final public String getId() {
        return _id;
    }

    final public String getName() {
        return _name;
    }

    final public String getNetwork() {
        return _network;
    }

    final public List<String> getNodes() {
        return _nodes;
    }

}
