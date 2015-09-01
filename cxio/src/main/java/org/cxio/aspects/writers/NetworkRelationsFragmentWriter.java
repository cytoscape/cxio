package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class NetworkRelationsFragmentWriter extends AbstractFragmentWriter {

    public static NetworkRelationsFragmentWriter createInstance() {
        return new NetworkRelationsFragmentWriter();
    }

    private NetworkRelationsFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NetworkRelationsElement na = (NetworkRelationsElement) element;

        w.writeStartObject();
        w.writeStringField(NetworkRelationsElement.PARENT, na.getParent());
        w.writeStringField(NetworkRelationsElement.CHILD, na.getChild());
        w.writeStringField(NetworkRelationsElement.RELATIONSHIP, na.getRelationship());
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return NetworkRelationsElement.NAME;
    }

}
