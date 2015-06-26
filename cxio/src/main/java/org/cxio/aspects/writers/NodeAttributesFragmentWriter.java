package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;

public class NodeAttributesFragmentWriter extends AbstractAspectFragmentWriter {

    private AspectKeyFilter _filter;

    public static NodeAttributesFragmentWriter createInstance() {
        return new NodeAttributesFragmentWriter();
    }

    private NodeAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NodeAttributesElement na = (NodeAttributesElement) element;
        w.writeStartObject();
        w.writeStringField(AbstractAttributesElement.ID, na.getId());
        w.writeList(NodeAttributesElement.NODES, na.getNodes());
        if ((na.getAttributesTypes() != null) && !na.getAttributesTypes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTE_TYPES);
            for (final Entry<String, ATTRIBUTE_TYPE> a : na.getAttributesTypes().entrySet()) {
                if ((_filter == null) || _filter.isPass(a.getKey())) {
                    w.writeStringField(a.getKey(), a.getValue().toString());
                }
            }
            w.writeEndObject();
        }

        if ((na.getAttributes() != null) && !na.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTES);
            for (final Entry<String, List<String>> a : na.getAttributes().entrySet()) {
                if ((_filter == null) || _filter.isPass(a.getKey())) {
                    w.writeList(a.getKey(), a.getValue());
                }
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public String getAspectName() {
        return NodeAttributesElement.NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }
}
