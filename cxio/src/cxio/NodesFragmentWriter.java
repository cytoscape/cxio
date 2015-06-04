package cxio;

import java.io.IOException;
import java.util.List;

import cxio.AspectElement;
import cxio.AspectFragmentWriter;
import cxio.Cx;
import cxio.JsonWriter;
import cxio.NodeElement;

public class NodesFragmentWriter implements AspectFragmentWriter {

    public static NodesFragmentWriter createInstance(final JsonWriter w) {
        return new NodesFragmentWriter(w);
    }

    final private JsonWriter w;

    private NodesFragmentWriter(final JsonWriter w) {
        this.w = w;
    }

    private final void addNode(final String node_id) throws IOException {
        w.writeStartObject();
        w.writeStringField(Cx.ID, node_id);
        w.writeEndObject();
    }

    @Override
    public final void write(final List<AspectElement> node_aspects) throws IOException {
        if (node_aspects == null) {
            return;
        }
        w.startArray(Cx.NODES);
        for (final AspectElement node_aspect : node_aspects) {
            addNode(((NodeElement) node_aspect).getId());
        }
        w.endArray();
    }

}
