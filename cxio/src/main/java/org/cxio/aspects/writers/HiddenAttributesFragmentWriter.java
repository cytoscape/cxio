package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class HiddenAttributesFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static HiddenAttributesFragmentWriter createInstance() {
        return new HiddenAttributesFragmentWriter();
    }

    private HiddenAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final HiddenAttributesElement e = (HiddenAttributesElement) element;
        if ((e.getValues() != null) && (!e.getValues().isEmpty()) && ((_filter == null) || _filter.isPass(e.getName()))) {
            w.writeStartObject();
            w.writeStringFieldIfNotEmpty(AbstractAttributesElement.ATTR_SUBNETWORK, e.getSubnetwork());
            w.writeStringField(AbstractAttributesElement.ATTR_NAME, e.getName());
            if (e.getValues().size() == 1) {
                w.writeStringField(AbstractAttributesElement.ATTR_VALUES, e.getValues().get(0));
            }
            else {
                w.writeList(AbstractAttributesElement.ATTR_VALUES, e.getValues());
            }
            if (e.getDataType() != ATTRIBUTE_TYPE.STRING) {
                w.writeStringField(AbstractAttributesElement.ATTR_DATA_TYPE, e.getDataType().toString());
            }
            w.writeEndObject();
        }
    }

    @Override
    public String getAspectName() {
        return HiddenAttributesElement.NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }

}
