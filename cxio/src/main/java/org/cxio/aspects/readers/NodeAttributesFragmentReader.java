package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NodeAttributesFragmentReader extends AbstractFragmentReader {

    public static NodeAttributesFragmentReader createInstance() {
        return new NodeAttributesFragmentReader();
    }

    private NodeAttributesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NodeAttributesElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesElement.ATTR_DATA_TYPE)) {
            type = AbstractAttributesElement.toDataType(ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_DATA_TYPE));
        }
        return new NodeAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesElement.ATTR_SUBNETWORK),
                                         ParserUtils.getAsStringListRequired(o, AbstractAttributesElement.ATTR_PROPERTY_OF),
                                         ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_NAME),
                                         ParserUtils.getAsStringList(o, AbstractAttributesElement.ATTR_VALUES),
                                         type);
    }

}
