package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class NetworkRelationsFragmentWriter extends AbstractAspectFragmentWriter {

    public static NetworkRelationsFragmentWriter createInstance() {
        return new NetworkRelationsFragmentWriter();
    }

    public static NetworkRelationsFragmentWriter createInstance(final String time_stamp) {
        final NetworkRelationsFragmentWriter w = new NetworkRelationsFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private NetworkRelationsFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NetworkRelationsElement na = (NetworkRelationsElement) element;

        w.writeStartObject();
        w.writeStringField(NetworkRelationsElement.PARENT, na.getParent());
        w.writeStringField(NetworkRelationsElement.CHILD, na.getChild());
        w.writeStringField(NetworkRelationsElement.TYPE, na.getType());
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return NetworkRelationsElement.NAME;
    }

}
