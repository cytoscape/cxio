package org.cxio.examples.custom_aspects;

import java.io.IOException;
import java.util.Map;

import org.cxio.aspects.writers.AbstractFragmentWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.JsonWriter;

public class ContextFragmentWriter extends AbstractFragmentWriter {

    public static ContextFragmentWriter createInstance() {
        return new ContextFragmentWriter();
    }

    private ContextFragmentWriter() {
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final ContextElement e = (ContextElement) element;
        w.writeStartObject();
        for (final Map.Entry<String, String> entry : e.getContextKeyValues().entrySet()) {
            w.writeStringFieldIfNotEmpty(entry.getKey(), entry.getValue());
        }
        w.writeEndObject();

    }

    @Override
    public String getAspectName() {
        return ContextElement.NAME;
    }

}
