package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyGroupElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class GroupFragmentWriter extends AbstractFragmentWriter {

    public static GroupFragmentWriter createInstance() {
        return new GroupFragmentWriter();
    }

    private GroupFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CyGroupElement e = (CyGroupElement) element;
        w.writeStartObject();

        w.writeStringFieldIfNotEmpty(CyGroupElement.GROUP_NAME, e.getName());
        w.writeStringFieldIfNotEmpty(CyGroupElement.VIEW, e.getView());
        if (e.getNodes().size() == 1) {
            w.writeStringField(CyGroupElement.NODES, e.getNodes().get(0));
        }
        else {
            w.writeList(CyGroupElement.NODES, e.getNodes());
        }
        if (e.getExternalEdges().size() == 1) {
            w.writeStringField(CyGroupElement.EXTERNAL_EDGES, e.getExternalEdges().get(0));
        }
        else {
            w.writeList(CyGroupElement.EXTERNAL_EDGES, e.getExternalEdges());
        }
        if (e.getInternalEdges().size() == 1) {
            w.writeStringField(CyGroupElement.INTERNAL_EDGES, e.getInternalEdges().get(0));
        }
        else {
            w.writeList(CyGroupElement.INTERNAL_EDGES, e.getInternalEdges());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return CyGroupElement.NAME;
    }

}
