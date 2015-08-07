package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.GroupElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class GroupFragmentWriter extends AbstractAspectFragmentWriter {

    public static GroupFragmentWriter createInstance() {
        return new GroupFragmentWriter();
    }

    private GroupFragmentWriter() {
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final GroupElement e = (GroupElement) element;
        w.writeStartObject();
        w.writeStringFieldIfNotEmpty(GroupElement.GROUP_ID, e.getId());
        w.writeStringFieldIfNotEmpty(GroupElement.GROUP_NAME, e.getName());
        w.writeStringFieldIfNotEmpty(GroupElement.GROUP_NETWORK, e.getNetwork());
        if (e.getNodes().size() == 1) {
            w.writeStringField(GroupElement.GROUP_NODES, e.getNodes().get(0));
        }
        else {
            w.writeList(GroupElement.GROUP_NODES, e.getNodes());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return GroupElement.NAME;
    }

}
