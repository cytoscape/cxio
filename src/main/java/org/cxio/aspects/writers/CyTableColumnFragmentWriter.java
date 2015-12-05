package org.cxio.aspects.writers;

import java.io.IOException;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.CyTableColumnElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.util.JsonWriter;

public class CyTableColumnFragmentWriter extends AbstractFragmentWriter {

    private AspectKeyFilter _filter;

    public static CyTableColumnFragmentWriter createInstance() {
        return new CyTableColumnFragmentWriter();
    }

    private CyTableColumnFragmentWriter() {
        _filter = null;
    }

    @Override
    public void writeElement(final AspectElement element, final JsonWriter w) throws IOException {
       writeAttributesElement(w, (CyTableColumnElement) element, _filter, false);
    }

    @Override
    public String getAspectName() {
        return CyTableColumnElement.ASPECT_NAME;
    }

    @Override
    public void addAspectKeyFilter(final AspectKeyFilter filter) {
        _filter = filter;
    }
    
    private static final void writeAttributesElement(final JsonWriter w, final AbstractAttributesAspectElement e, final AspectKeyFilter filter, final boolean write_property_of) throws IOException {
        if ((filter == null) || filter.isPass(e.getName())) {
            w.writeStartObject();
            w.writeNumberFieldIfNotEmpty(AbstractAttributesAspectElement.ATTR_SUBNETWORK, e.getSubnetwork());
            w.writeStringField(AbstractAttributesAspectElement.ATTR_NAME, e.getName());
            if (e.getDataType() != ATTRIBUTE_DATA_TYPE.STRING) {
                w.writeStringField(AbstractAttributesAspectElement.ATTR_DATA_TYPE, ATTRIBUTE_DATA_TYPE.toCxLabel(e.getDataType()));
            }
            w.writeEndObject();
        }
    }

}
