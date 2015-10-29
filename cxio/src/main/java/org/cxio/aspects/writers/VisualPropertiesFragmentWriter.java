package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class VisualPropertiesFragmentWriter extends AbstractFragmentWriter {

    public static VisualPropertiesFragmentWriter createInstance() {
        return new VisualPropertiesFragmentWriter();
    }

    private VisualPropertiesFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return CyVisualPropertiesElement.ASPECT_NAME;
    }

    @Override
    public final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CyVisualPropertiesElement c = (CyVisualPropertiesElement) element;
        w.writeStartObject();
        w.writeStringField(CyVisualPropertiesElement.PROPERTIES_OF, c.getPropertiesOf());
        if (c.getAppliesTo().size() == 1) {
            w.writeStringField(CyVisualPropertiesElement.APPLIES_TO, c.getAppliesTo().get(0));
        }
        else if (c.getAppliesTo().size() > 1) {
            w.writeList(CyVisualPropertiesElement.APPLIES_TO, c.getAppliesTo());
        }
        w.writeStringFieldIfNotEmpty(CyVisualPropertiesElement.VIEW, c.getView());
        if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
            w.writeObjectFieldStart(CyVisualPropertiesElement.PROPERTIES);
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
