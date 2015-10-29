package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class CyViewsFragmentWriter extends AbstractFragmentWriter {

    public static CyViewsFragmentWriter createInstance() {
        return new CyViewsFragmentWriter();
    }

    private CyViewsFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return CyViewsElement.ASPECT_NAME;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CyViewsElement e = (CyViewsElement) element;
        w.writeStartObject();
        w.writeStringField(CyViewsElement.SUBWORKNET_ID, e.getSubnetworkId());
        w.writeEndObject();
    }
}
