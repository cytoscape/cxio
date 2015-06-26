package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class NodesFragmentWriter extends AbstractAspectFragmentWriter {

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
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(NodesElement.ID, ((NodesElement) element).getId());
        w.writeEndObject();

    }

}
