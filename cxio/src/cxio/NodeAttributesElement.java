package cxio;

import java.util.List;
import java.util.SortedMap;

public final class NodeAttributesElement implements AspectElement {

    private final String                          id;
    private final List<String>                    nodes;
    private final SortedMap<String, List<String>> attributes;

    public NodeAttributesElement(final String id, final List<String> nodes,
            final SortedMap<String, List<String>> attributes) {
        this.id = id;
        this.nodes = nodes;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return ((o instanceof NodeAttributesElement) && id.equals(((NodeAttributesElement) o).getId()));

    }

    @Override
    public String getAspectName() {
        return Cx.NODE_ATTRIBUTES;
    }

    public final SortedMap<String, List<String>> getAttributes() {
        return attributes;
    }

    public final String getId() {
        return id;
    }

    public final List<String> getNodes() {
        return nodes;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(id);
        sb.append("\n");
        sb.append("nodes:");
        sb.append("\n");
        sb.append(nodes);
        sb.append("\n");
        sb.append("attributes:");
        sb.append("\n");
        sb.append(attributes);
        return sb.toString();
    }

}
