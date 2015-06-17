package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;

public class EdgeAttributesFragmentWriter implements AspectFragmentWriter {
    public static EdgeAttributesFragmentWriter createInstance() {
        return new EdgeAttributesFragmentWriter();
    }

    private EdgeAttributesFragmentWriter() {
    }

    private final void addEdgeAttributesAspect(final EdgeAttributesElement ea, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(CxConstants.ID, ea.getId());
        w.writeList(EdgeAttributesElement.EDGES, ea.getEdges());
        if ((ea.getAttributesTypes() != null) && !ea.getAttributesTypes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTE_TYPES);
            for (final Entry<String, ATTRIBUTE_TYPE> a : ea.getAttributesTypes().entrySet()) {
                w.writeStringField(a.getKey(), a.getValue().toString());
            }
            w.writeEndObject();
        }

        if ((ea.getAttributes() != null) && !ea.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTES);
            for (final Entry<String, List<String>> a : ea.getAttributes().entrySet()) {
                w.writeList(a.getKey(), a.getValue());
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> edge_attributes_aspects, final JsonWriter w) throws IOException {
        if (edge_attributes_aspects == null) {
            return;
        }
        w.startArray(EdgeAttributesElement.EDGE_ATTRIBUTES);
        for (final AspectElement edge_attributes_aspect : edge_attributes_aspects) {
            final EdgeAttributesElement ea = (EdgeAttributesElement) edge_attributes_aspect;
            addEdgeAttributesAspect(ea, w);
        }
        w.endArray();
    }

    @Override
    public String getAspectName() {
        return EdgeAttributesElement.EDGE_ATTRIBUTES;
    }

}
