package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.tools.Util;

public class EdgesFragmentWriter extends AbstractAspectFragmentWriter {

    public static EdgesFragmentWriter createInstance() {
        return new EdgesFragmentWriter();
    }

    private EdgesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return EdgesElement.NAME;
    }

    @Override
    void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final EdgesElement e = (EdgesElement) element;
        w.writeStartObject();
        final String edge_id = e.getId();
        if (!Util.isEmpty(edge_id)) {
            w.writeStringField(EdgesElement.ID, edge_id);
        }
        w.writeStringField(EdgesElement.SOURCE_NODE_ID, e.getSource());
        w.writeStringField(EdgesElement.TARGET_NODE_ID, e.getTarget());
        w.writeEndObject();

    }

}
