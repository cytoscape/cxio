package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class NodeAttributesFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static NodeAttributesFragmentWriter createInstance() {
        return new NodeAttributesFragmentWriter();
    }

    private NodeAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NodeAttributesElement e = (NodeAttributesElement) element;

        if ((_filter == null) || _filter.isPass(e.getName())) {

            final boolean is_single = e.isSingleValue();

            if ((is_single && ((e.getValue() != null))) || (!is_single && ((e.getValues() != null) && (!e.getValues().isEmpty())))) {
                w.writeStartObject();
                w.writeStringFieldIfNotEmpty(AbstractAttributesAspectElement.ATTR_SUBNETWORK, e.getSubnetwork());
                if (e.getPropertyOf().size() == 1) {
                    w.writeStringField(AbstractAttributesAspectElement.ATTR_PROPERTY_OF, e.getPropertyOf().get(0));
                }
                else {
                    w.writeList(AbstractAttributesAspectElement.ATTR_PROPERTY_OF, e.getPropertyOf());
                }
                w.writeStringField(AbstractAttributesAspectElement.ATTR_NAME, e.getName());
                if (is_single) {
                    w.writeStringField(AbstractAttributesAspectElement.ATTR_VALUES, e.getValue());
                }
                else {
                    w.writeList(AbstractAttributesAspectElement.ATTR_VALUES, e.getValues());
                }
                if (e.getDataType() != ATTRIBUTE_DATA_TYPE.STRING) {
                    w.writeStringField(AbstractAttributesAspectElement.ATTR_DATA_TYPE, AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE.toCxLabel(e.getDataType()));
                }
                w.writeEndObject();
            }
        }
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
