package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class EdgeAttributesFragmentReader extends AbstractFragmentReader {

    public static EdgeAttributesFragmentReader createInstance() {
        return new EdgeAttributesFragmentReader();
    }

    private EdgeAttributesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return EdgeAttributesElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesElement.ATTR_TYPE)) {
            type = AbstractAttributesElement.toType(ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_TYPE));
        }
        return new EdgeAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesElement.ATTR_SUBNETWORK),
                                         ParserUtils.getAsStringListRequired(o, AbstractAttributesElement.ATTR_PROPERTY_OF),
                                         ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_NAME),
                                         ParserUtils.getAsStringList(o, AbstractAttributesElement.ATTR_VALUES),
                                         type);
    }

}
