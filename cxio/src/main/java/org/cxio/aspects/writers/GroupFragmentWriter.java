package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class GroupFragmentWriter extends AbstractAspectFragmentWriter {

    public static GroupFragmentWriter createInstance() {
        return new GroupFragmentWriter();
    }

    public static GroupFragmentWriter createInstance(final String time_stamp) {
        final GroupFragmentWriter w = new GroupFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private GroupFragmentWriter() {
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final GroupElement e = (GroupElement) element;
        w.writeStartObject();
        w.writeStringFieldIfNotEmpty(GroupElement.GROUP_NODE, e.getGroupNode());
        w.writeStringFieldIfNotEmpty(GroupElement.GROUP_NAME, e.getName());
        w.writeStringFieldIfNotEmpty(GroupElement.VIEW, e.getView());
        if (e.getNodes().size() == 1) {
            w.writeStringField(GroupElement.NODES, e.getNodes().get(0));
        }
        else {
            w.writeList(GroupElement.NODES, e.getNodes());
        }
        if (e.getExternalEdges().size() == 1) {
            w.writeStringField(GroupElement.EXTERNAL_EDGES, e.getExternalEdges().get(0));
        }
        else {
            w.writeList(GroupElement.EXTERNAL_EDGES, e.getExternalEdges());
        }
        if (e.getInternalEdges().size() == 1) {
            w.writeStringField(GroupElement.INTERNAL_EDGES, e.getInternalEdges().get(0));
        }
        else {
            w.writeList(GroupElement.INTERNAL_EDGES, e.getInternalEdges());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return GroupElement.NAME;
    }

}
