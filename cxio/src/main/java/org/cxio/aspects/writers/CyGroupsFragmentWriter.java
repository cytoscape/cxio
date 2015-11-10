package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class CyGroupsFragmentWriter extends AbstractFragmentWriter {

    public static CyGroupsFragmentWriter createInstance() {
        return new CyGroupsFragmentWriter();
    }

    private CyGroupsFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CyGroupsElement e = (CyGroupsElement) element;
        w.writeStartObject();

        w.writeNumberFieldIfNotEmpty(CyGroupsElement.GROUP_ID, e.getGroupId());
        w.writeNumberFieldIfNotEmpty(CyGroupsElement.VIEW, e.getView());
        w.writeStringFieldIfNotEmpty(CyGroupsElement.GROUP_NAME, e.getName());

        if (e.getNodes().size() == 1) {
            w.writeNumberField(CyGroupsElement.NODES, e.getNodes().get(0));
        }
        else {
            w.writeLongList(CyGroupsElement.NODES, e.getNodes());
        }
        if (e.getExternalEdges().size() == 1) {
            w.writeNumberField(CyGroupsElement.EXTERNAL_EDGES, e.getExternalEdges().get(0));
        }
        else {
            w.writeLongList(CyGroupsElement.EXTERNAL_EDGES, e.getExternalEdges());
        }
        if (e.getInternalEdges().size() == 1) {
            w.writeNumberField(CyGroupsElement.INTERNAL_EDGES, e.getInternalEdges().get(0));
        }
        else {
            w.writeLongList(CyGroupsElement.INTERNAL_EDGES, e.getInternalEdges());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return CyGroupsElement.ASPECT_NAME;
    }

}
