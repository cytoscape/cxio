package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class SubNetworkFragmentWriter extends AbstractFragmentWriter {

    public static SubNetworkFragmentWriter createInstance() {
        return new SubNetworkFragmentWriter();
    }

    private SubNetworkFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final SubNetworkElement e = (SubNetworkElement) element;
        w.writeStartObject();
        w.writeStringFieldIfNotEmpty(SubNetworkElement.SUBNET_ID, e.getId());

        if (e.getNodes().size() == 1) {
            w.writeStringField(SubNetworkElement.SUBNET_NODES, e.getNodes().get(0));
        }
        else {
            w.writeList(SubNetworkElement.SUBNET_NODES, e.getNodes());
        }
        if (e.getEdges().size() == 1) {
            w.writeStringField(SubNetworkElement.SUBNET_EDGES, e.getEdges().get(0));
        }
        else {
            w.writeList(SubNetworkElement.SUBNET_EDGES, e.getEdges());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return SubNetworkElement.ASPECT_NAME;
    }

}
