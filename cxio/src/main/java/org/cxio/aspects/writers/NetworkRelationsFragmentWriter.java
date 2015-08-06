package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class NetworkRelationsFragmentWriter extends AbstractAspectFragmentWriter {

    public static NetworkRelationsFragmentWriter createInstance() {
        return new NetworkRelationsFragmentWriter();
    }

    private NetworkRelationsFragmentWriter() {
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NetworkRelationsElement na = (NetworkRelationsElement) element;

        w.writeStartObject();

        w.writeStringField(NetworkRelationsElement.PARENT, na.getParent());
        w.writeStringField(NetworkRelationsElement.CHILD, na.getChild());

        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return NetworkRelationsElement.NAME;
    }

}
