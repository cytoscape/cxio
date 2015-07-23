package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.datamodels.VisualProperties;
import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;

public class CytoscapeVisualStyleFragmentWriter extends AbstractAspectFragmentWriter {

    public static CytoscapeVisualStyleFragmentWriter createInstance() {
        return new CytoscapeVisualStyleFragmentWriter();
    }

    private CytoscapeVisualStyleFragmentWriter() {
    }

    @Override
    public String getAspectName() {
        return VisualPropertiesElement.NAME;
    }

    @Override
    protected final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final VisualPropertiesElement c = (VisualPropertiesElement) element;
        w.writeStartObject();
        w.writeStringField(VisualPropertiesElement.TITLE, c.getTitle());

        if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
            w.writeStartArray(VisualPropertiesElement.STYLES);
            for (final VisualProperties property : c.getProperties()) {
                w.writeStartObject();
                w.writeStringField(VisualPropertiesElement.APPLIES_TO, property.getAppliesTo());
                w.writeStringField(VisualPropertiesElement.SELECTOR, property.getSelector());
                if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
                    w.writeObjectFieldStart(VisualPropertiesElement.PROPERTIES);
                    for (final Map.Entry<String, String> entry : property.getProperties().entrySet()) {
                        if (entry.getValue() != null) {
                            w.writeStringField(entry.getKey(), entry.getValue());
                        }
                    }
                    w.writeEndObject();
                }
                w.writeEndObject();
            }
            w.writeEndArray();
        }
        w.writeEndObject();
    }

}
