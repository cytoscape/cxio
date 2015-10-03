package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
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

        final EdgeAttributesElement e = (EdgeAttributesElement) element;

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
                    w.writeStringField(AbstractAttributesAspectElement.ATTR_DATA_TYPE, e.getDataType().toString());
                }
                w.writeEndObject();
            }
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
