package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class NodesFragmentWriter extends AbstractFragmentWriter {

    public static NodesFragmentWriter createInstance() {
        return new NodesFragmentWriter();
    }

    public static NodesFragmentWriter createInstance(final String time_stamp) {
        final NodesFragmentWriter w = new NodesFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private NodesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return NodesElement.NAME;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(NodesElement.ID, ((NodesElement) element).getId());
        w.writeEndObject();
    }

}
