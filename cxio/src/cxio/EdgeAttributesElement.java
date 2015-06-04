package cxio;

import java.util.List;
import java.util.SortedMap;

public final class EdgeAttributesElement implements AspectElement {

    private final String                          id;
    private final List<String>                    edges;
    private final SortedMap<String, List<String>> attributes;

    public EdgeAttributesElement(final String id, final List<String> edges,
            final SortedMap<String, List<String>> attributes) {
        this.id = id;
        this.edges = edges;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return ((o instanceof EdgeAttributesElement) && id.equals(((EdgeAttributesElement) o).getId()));

    }

    @Override
    public String getAspectName() {
        return Cx.EDGE_ATTRIBUTES;
    }

    public final SortedMap<String, List<String>> getAttributes() {
        return attributes;
    }

    public final List<String> getEdges() {
        return edges;
    }

    public final String getId() {
        return id;
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
        sb.append("edges:");
        sb.append("\n");
        sb.append(edges);
        sb.append("\n");
        sb.append("attributes:");
        sb.append("\n");
        sb.append(attributes);
        return sb.toString();
    }

}
