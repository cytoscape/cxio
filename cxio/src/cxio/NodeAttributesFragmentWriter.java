package cxio;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

public class NodeAttributesFragmentWriter implements AspectFragmentWriter {
    public static NodeAttributesFragmentWriter createInstance(final JsonWriter w) {
        return new NodeAttributesFragmentWriter(w);
    }

    final private JsonWriter w;

    private NodeAttributesFragmentWriter(final JsonWriter w) {
        this.w = w;
    }

    private final void addNodeAttributesAspect(final NodeAttributesElement na) throws IOException {
        w.writeStartObject();
        w.writeStringField(Cx.ID, na.getId());
        w.writeList(Cx.NODES, na.getNodes());
        if ((na.getAttributes() != null) && !na.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(Cx.ATTRIBUTES);
            for (final Entry<String, List<String>> a : na.getAttributes().entrySet()) {
                w.writeList(a.getKey(), a.getValue());
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> node_attributes_aspects) throws IOException {
        if (node_attributes_aspects == null) {
            return;
        }
        w.startArray(Cx.NODE_ATTRIBUTES);
        for (final AspectElement node_attributes_aspect : node_attributes_aspects) {
            final NodeAttributesElement na = (NodeAttributesElement) node_attributes_aspect;
            addNodeAttributesAspect(na);
        }
        w.endArray();
    }

}
