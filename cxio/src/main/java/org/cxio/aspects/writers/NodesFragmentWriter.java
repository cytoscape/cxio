package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class NodesFragmentWriter extends AbstractFragmentWriter {

    public static NodesFragmentWriter createInstance() {
        return new NodesFragmentWriter();
    }

    private NodesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return NodesElement.NAME;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NodesElement e = (NodesElement) element;
        w.writeStartObject();
        w.writeStringField(NodesElement.ID, e.getId());
        if (e.getNodeName() != null) {
            w.writeStringField(NodesElement.NODE_NAME, e.getNodeName());
        }
        w.writeEndObject();
    }
}
