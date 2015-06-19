package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.tools.Util;

public class EdgesFragmentWriter implements AspectFragmentWriter {

    public static EdgesFragmentWriter createInstance() {
        return new EdgesFragmentWriter();
    }

    private EdgesFragmentWriter() {
    }

    private final void addEdge(final String edge_id,
                               final String source_node_id,
                               final String target_node_id,
                               final JsonWriter w) throws IOException {
        w.writeStartObject();
        if (!Util.isEmpty(edge_id)) {
            w.writeStringField(CxConstants.ID, edge_id);
        }
        w.writeStringField(EdgesElement.SOURCE_NODE_ID, source_node_id);
        w.writeStringField(EdgesElement.TARGET_NODE_ID, target_node_id);
        w.writeEndObject();

    }

    @Override
    public void write(final List<AspectElement> edge_aspects, final JsonWriter w) throws IOException {
        if (edge_aspects == null) {
            return;
        }
        w.startArray(EdgesElement.EDGES);
        for (final AspectElement edge_aspect : edge_aspects) {
            final EdgesElement e = (EdgesElement) edge_aspect;
            addEdge(e.getId(), e.getSource(), e.getTarget(), w);
        }
        w.endArray();

    }

    @Override
    public String getAspectName() {
        return EdgesElement.EDGES;
    }

}
