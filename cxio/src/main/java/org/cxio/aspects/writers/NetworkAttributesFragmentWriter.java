package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class NetworkAttributesFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static NetworkAttributesFragmentWriter createInstance() {
        return new NetworkAttributesFragmentWriter();
    }

    private NetworkAttributesFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
        final NetworkAttributesElement e = (NetworkAttributesElement) element;

        if ((_filter == null) || _filter.isPass(e.getName())) {

            final boolean is_single = e.isSingleValue();

            if ((is_single && ((e.getValue() != null))) || (!is_single && ((e.getValues() != null) && (!e.getValues().isEmpty())))) {
                w.writeStartObject();
                w.writeStringFieldIfNotEmpty(AbstractAttributesAspectElement.ATTR_SUBNETWORK, e.getSubnetwork());
                w.writeStringField(AbstractAttributesAspectElement.ATTR_NAME, e.getName());
                if (is_single) {
                    w.writeStringFieldIfNotEmpty(AbstractAttributesAspectElement.ATTR_VALUES, e.getValue());
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
        return NetworkAttributesElement.NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }

}
