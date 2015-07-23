package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.datamodels.CytoscapeVisualProperties;
import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
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
        return CytoscapeVisualStyleElement.NAME;
    }

    @Override
    protected final void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final CytoscapeVisualStyleElement c = (CytoscapeVisualStyleElement) element;
        w.writeStartObject();
        w.writeStringField(CytoscapeVisualStyleElement.TITLE, c.getTitle());

        if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
            w.writeStartArray(CytoscapeVisualStyleElement.STYLES);
            for (final CytoscapeVisualProperties property : c.getProperties()) {
                w.writeStartObject();
                w.writeStringField(CytoscapeVisualStyleElement.APPLIES_TO, property.getAppliesTo());
                w.writeStringField(CytoscapeVisualStyleElement.SELECTOR, property.getSelector());
                if ((c.getProperties() != null) && !c.getProperties().isEmpty()) {
                    w.writeObjectFieldStart(CytoscapeVisualStyleElement.PROPERTIES);
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
