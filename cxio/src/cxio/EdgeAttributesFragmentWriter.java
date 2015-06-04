package cxio;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

public class EdgeAttributesFragmentWriter implements AspectFragmentWriter {
    public static EdgeAttributesFragmentWriter createInstance(final JsonWriter w) {
        return new EdgeAttributesFragmentWriter(w);
    }

    final private JsonWriter w;

    private EdgeAttributesFragmentWriter(final JsonWriter w) {
        this.w = w;
    }

    private final void addEdgeAttributesAspect(final EdgeAttributesElement ea) throws IOException {
        w.writeStartObject();
        w.writeStringField(Cx.ID, ea.getId());
        w.writeList(Cx.EDGES, ea.getEdges());
        if ((ea.getAttributes() != null) && !ea.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(Cx.ATTRIBUTES);
            for (final Entry<String, List<String>> a : ea.getAttributes().entrySet()) {
                w.writeList(a.getKey(), a.getValue());
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> edge_attributes_aspects) throws IOException {
        if (edge_attributes_aspects == null) {
            return;
        }
        w.startArray(Cx.EDGE_ATTRIBUTES);
        for (final AspectElement edge_attributes_aspect : edge_attributes_aspects) {
            final EdgeAttributesElement ea = (EdgeAttributesElement) edge_attributes_aspect;
            addEdgeAttributesAspect(ea);
        }
        w.endArray();
    }

}
