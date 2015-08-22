package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class NetworkAttributesFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static NetworkAttributesFragmentWriter createInstance() {
        return new NetworkAttributesFragmentWriter();
    }

    public static NetworkAttributesFragmentWriter createInstance(final String time_stamp) {
        final NetworkAttributesFragmentWriter w = new NetworkAttributesFragmentWriter();
        w.setTimeStamp(time_stamp);
        return w;
    }

    private NetworkAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NetworkAttributesElement na = (NetworkAttributesElement) element;
        if ((na.getValues() != null) && (!na.getValues().isEmpty()) && ((_filter == null) || _filter.isPass(na.getName()))) {
            w.writeStartObject();
            if (na.getPropertyOf().size() == 1) {
                w.writeStringField(AbstractAttributesElement.ATTR_PROPERTY_OF, na.getPropertyOf().get(0));
            }
            else {
                w.writeList(AbstractAttributesElement.ATTR_PROPERTY_OF, na.getPropertyOf());
            }
            w.writeStringField(AbstractAttributesElement.ATTR_NAME, na.getName());
            if (na.getValues().size() == 1) {
                w.writeStringField(AbstractAttributesElement.ATTR_VALUES, na.getValues().get(0));
            }
            else {
                w.writeList(AbstractAttributesElement.ATTR_VALUES, na.getValues());
            }
            if (na.getType() != ATTRIBUTE_TYPE.STRING) {
                w.writeStringField(AbstractAttributesElement.ATTR_TYPE, na.getType().toString());
            }
            w.writeEndObject();
        }
    }

    @Override
    public String getAspectName() {
        return NetworkAttributesElement.NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }

}
