package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class EdgesFragmentWriter extends AbstractFragmentWriter {

    public static EdgesFragmentWriter createInstance() {
        return new EdgesFragmentWriter();
    }

    private EdgesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return EdgesElement.ASPECT_NAME;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final EdgesElement e = (EdgesElement) element;
        w.writeStartObject();
        w.writeStringField(EdgesElement.ID, e.getId());
        w.writeStringField(EdgesElement.SOURCE_NODE_ID, e.getSource());
        w.writeStringField(EdgesElement.TARGET_NODE_ID, e.getTarget());
        w.writeStringFieldIfNotEmpty(EdgesElement.INTERACTION, e.getInteraction());
        w.writeEndObject();

    }

}
