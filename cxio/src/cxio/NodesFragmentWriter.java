package cxio;

import java.io.IOException;
import java.util.List;

import cxio.AspectElement;
import cxio.AspectFragmentWriter;
import cxio.CxConstants;
import cxio.JsonWriter;
import cxio.NodesElement;

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
        w.writeStringField(CxConstants.ID, node_id);
        w.writeEndObject();
    }

    @Override
    public final void write(final List<AspectElement> node_aspects) throws IOException {
        if (node_aspects == null) {
            return;
        }
        w.startArray(CxConstants.NODES);
        for (final AspectElement node_aspect : node_aspects) {
            addNode(((NodesElement) node_aspect).getId());
        }
        w.endArray();
    }

}
