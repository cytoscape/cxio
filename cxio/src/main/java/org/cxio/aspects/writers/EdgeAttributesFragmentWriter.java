package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;

public class EdgeAttributesFragmentWriter extends AbstractAspectFragmentWriter {

    private AspectKeyFilter _filter;

    public static EdgeAttributesFragmentWriter createInstance() {
        return new EdgeAttributesFragmentWriter();
    }

    private EdgeAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    protected void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final EdgeAttributesElement ea = (EdgeAttributesElement) element;
        w.writeStartObject();
        w.writeStringField(EdgeAttributesElement.ID, ea.getId());
        w.writeList(EdgeAttributesElement.EDGES, ea.getEdges());

        if ((ea.getAttributesTypes() != null) && !ea.getAttributesTypes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTE_TYPES);
            for (final Entry<String, ATTRIBUTE_TYPE> a : ea.getAttributesTypes().entrySet()) {
                if ((_filter == null) || _filter.isPass(a.getKey())) {
                    w.writeStringField(a.getKey(), a.getValue().toString());
                }
            }
            w.writeEndObject();
        }

        if ((ea.getAttributes() != null) && !ea.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(AbstractAttributesElement.ATTRIBUTES);
            for (final Entry<String, List<String>> a : ea.getAttributes().entrySet()) {
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
        return EdgeAttributesElement.NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }

}
