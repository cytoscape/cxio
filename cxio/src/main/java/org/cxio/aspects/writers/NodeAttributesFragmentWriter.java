package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.tools.JsonWriter;

public class NodeAttributesFragmentWriter implements AspectFragmentWriter {
    public static NodeAttributesFragmentWriter createInstance() {
        return new NodeAttributesFragmentWriter();
    }

    private NodeAttributesFragmentWriter() {
    }

    private final void addNodeAttributesAspect(final NodeAttributesElement na, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(CxConstants.ID, na.getId());
        w.writeList(NodeAttributesElement.NODES, na.getNodes());
        if ((na.getAttributesTypes() != null) && !na.getAttributesTypes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTE_TYPES);
            for (final Entry<String, ATTRIBUTE_TYPE> a : na.getAttributesTypes().entrySet()) {
                w.writeStringField(a.getKey(), a.getValue().toString());
            }
            w.writeEndObject();
        }

        if ((na.getAttributes() != null) && !na.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTES);
            for (final Entry<String, List<String>> a : na.getAttributes().entrySet()) {
                w.writeList(a.getKey(), a.getValue());
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> node_attributes_aspects, final JsonWriter w) throws IOException {
        if (node_attributes_aspects == null) {
            return;
        }
        w.startArray(NodeAttributesElement.NODE_ATTRIBUTES);
        for (final AspectElement node_attributes_aspect : node_attributes_aspects) {
            final NodeAttributesElement na = (NodeAttributesElement) node_attributes_aspect;
            addNodeAttributesAspect(na, w);
        }
        w.endArray();
    }

    @Override
    public String getAspectName() {
        return NodeAttributesElement.NODE_ATTRIBUTES;
    }

}
