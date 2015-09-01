package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class EdgeAttributesFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static EdgeAttributesFragmentWriter createInstance() {
        return new EdgeAttributesFragmentWriter();
    }

    private EdgeAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final EdgeAttributesElement ea = (EdgeAttributesElement) element;
        if ((ea.getValues() != null) && (!ea.getValues().isEmpty()) && ((_filter == null) || _filter.isPass(ea.getName()))) {
            w.writeStartObject();
            w.writeStringFieldIfNotEmpty(AbstractAttributesElement.ATTR_SUBNETWORK, ea.getSubnetwork());
            if (ea.getPropertyOf().size() == 1) {
                w.writeStringField(AbstractAttributesElement.ATTR_PROPERTY_OF, ea.getPropertyOf().get(0));
            }
            else {
                w.writeList(AbstractAttributesElement.ATTR_PROPERTY_OF, ea.getPropertyOf());
            }
            w.writeStringField(AbstractAttributesElement.ATTR_NAME, ea.getName());
            if (ea.getValues().size() == 1) {
                w.writeStringField(AbstractAttributesElement.ATTR_VALUES, ea.getValues().get(0));
            }
            else {
                w.writeList(AbstractAttributesElement.ATTR_VALUES, ea.getValues());
            }
            if (ea.getDataType() != ATTRIBUTE_TYPE.STRING) {
                w.writeStringField(AbstractAttributesElement.ATTR_DATA_TYPE, ea.getDataType().toString());
            }
            w.writeEndObject();
        }
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
