package org.cxio.aspects.datamodels;

import org.cxio.util.CxConstants;

/**
 * This is used to represent a node in a network.
 *
 * @author cmzmasek
 *
 */
public final class NodesElement extends AbstractAspectElement {

    public final static String ID              = CxConstants.ID;
    public final static String NODE_NAME       = "n";
    public final static String NODE_REPRESENTS = "r";
    public final static String ASPECT_NAME     = "nodes";
    private final String       _id;
    private final String       _node_name;
    private final String       _node_represents;

    public NodesElement(final long id) {
        _id = String.valueOf(id);
        _node_name = null;
        _node_represents = null;
    }

    public NodesElement(final String id) {
        _id = id;
        _node_name = null;
        _node_represents = null;
    }

    public NodesElement(final long id, final String node_name) {
        _id = String.valueOf(id);
        _node_name = node_name;
        _node_represents = null;
    }

    public NodesElement(final String id, final String node_name) {
        _id = id;
        _node_name = node_name;
        _node_represents = null;
    }

    public NodesElement(final long id, final String node_name, final String node_represents) {
        _id = String.valueOf(id);
        _node_name = node_name;
        _node_represents = node_represents;
    }

    public NodesElement(final String id, final String node_name, final String node_represents) {
        _id = id;
        _node_name = node_name;
        _node_represents = node_represents;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof NodesElement) && _id.equals(((NodesElement) o).getId());
    }

    @Override
    final public String getAspectName() {
        return NodesElement.ASPECT_NAME;
    }

    final public String getId() {
        return _id;
    }

    final public String getNodeName() {
        return _node_name;
    }

    final public String getNodeRepresents() {
        return _node_represents;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (_node_name != null) {
            sb.append(_node_name);
            sb.append(" ");
        }
        if (_node_represents != null) {
            sb.append("represents: ");
            sb.append(_node_represents);
            sb.append(" ");
        }
        sb.append("id: ");
        sb.append(_id);
        return sb.toString();
    }

}
