package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class VisualPropertiesFragmentWriter extends AbstractAspectFragmentWriter {

    public static VisualPropertiesFragmentWriter createInstance() {
        return new VisualPropertiesFragmentWriter();
    }

    private VisualPropertiesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return VisualPropertiesElement.NAME;
    }

    @Override
    protected final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final VisualPropertiesElement c = (VisualPropertiesElement) element;
        w.writeStartObject();
        w.writeStringField(VisualPropertiesElement.PROPERTIES_OF, c.getPropertiesOf());
        w.writeList(VisualPropertiesElement.APPLIES_TO, c.getAppliesTo());
        if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
            w.writeObjectFieldStart(VisualPropertiesElement.PROPERTIES);
            for (final Map.Entry<String, String> entry : c.getProperties().entrySet()) {
                if (entry.getValue() != null) {
                    w.writeStringField(entry.getKey(), entry.getValue());
                }
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

}
