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

        w.writeStringFieldIfNotEmpty(CyGroupsElement.GROUP_ID, e.getGroupId());
        w.writeStringFieldIfNotEmpty(CyGroupsElement.VIEW, e.getView());
        w.writeStringFieldIfNotEmpty(CyGroupsElement.GROUP_NAME, e.getName());

        if (e.getNodes().size() == 1) {
            w.writeStringField(CyGroupsElement.NODES, e.getNodes().get(0));
        }
        else {
            w.writeList(CyGroupsElement.NODES, e.getNodes());
        }
        if (e.getExternalEdges().size() == 1) {
            w.writeStringField(CyGroupsElement.EXTERNAL_EDGES, e.getExternalEdges().get(0));
        }
        else {
            w.writeList(CyGroupsElement.EXTERNAL_EDGES, e.getExternalEdges());
        }
        if (e.getInternalEdges().size() == 1) {
            w.writeStringField(CyGroupsElement.INTERNAL_EDGES, e.getInternalEdges().get(0));
        }
        else {
            w.writeList(CyGroupsElement.INTERNAL_EDGES, e.getInternalEdges());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return CyGroupsElement.NAME;
    }

}
